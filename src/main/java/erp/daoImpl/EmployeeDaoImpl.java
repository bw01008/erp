package erp.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import erp.UI.exception.SqlConstraintException;
import erp.dao.EmployeeDao;
import erp.database.JdbcConn;
import erp.dto.Department;
import erp.dto.Employee;
import erp.dto.Title;

// 3. Employee 인터페이스를 구현하는 클래스
public class EmployeeDaoImpl implements EmployeeDao {
	// 싱글톤 객체 생성
	private static EmployeeDaoImpl instance = new EmployeeDaoImpl();

	// 외부에서 객체생성을 할 수 없게 private으로 설정해준 생성자
	private EmployeeDaoImpl() {
	}

	// 객체 주소를 반환해주는 메소드 (외부에서 접근해서 객체를 얻어가는 메소드)
	public static EmployeeDaoImpl getInstance() {
		if (instance == null) {
			instance = new EmployeeDaoImpl();
		}
		return instance;
	}

	// Employee테이블에 있는 모든 데이터를 검색해서 List에 담는 메소드
	@Override
	public List<Employee> selectEmployeeByAll() {
		String sql = "select empno,empname,title_no,title_name,manager_no, "
				+ "manager_name,salary,deptno,deptName, floor " + "from vw_full_employee"; // 전체검색하는 dml문을 문자열 변수에 저장
//		String sql = "select empno, empname, title as title_no, manager as manager_no ,salary,dept as deptNO from employee";
		try (Connection con = JdbcConn.getConnection(); // 1. DB연결
				PreparedStatement pstmt = con.prepareStatement(sql); // 2. 쿼리 쏠 준비
				ResultSet rs = pstmt.executeQuery()) { // 쿼리 보내고 반환받은 결과값을 저장
			if (rs.next()) { // 포인터가 가르키는 곳 다음에 데이터가 있다면~
				List<Employee> list = new ArrayList<>(); // List 생성
				do {
					list.add(getEmployee(rs)); // ArrayList에 데이터를 추가
				} while (rs.next()); // 포인터가 가르키는 곳 다음에 데이터가 있다면 반복문 실행
				return list; // list 반환
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Employee getEmployee(ResultSet rs){
		int empno = 0;
		String empname = null;
		Title title = null;
		Employee manager = null;
		int salary = 0;
		Department dept = null;
		
		try {
			empno = rs.getInt("empno");
		}catch(SQLException e) {}
		
		try {
			empname = rs.getString("empname");
		}catch(SQLException e) {}
		
		try {
			title = new Title(rs.getInt("title_no"));
		}catch(SQLException e) {}
		
		try {
			manager = new Employee(rs.getInt("manager_no"));
		}catch(SQLException e) {}
		
		try {
			salary = rs.getInt("salary");
		}catch(SQLException e) {}
		
		try {
			dept = new Department(rs.getInt("deptno"));
		}catch(SQLException e) {}
		
		try {
			title.setTname(rs.getString("title_name"));
		} catch (SQLException e) {}

		try {
			manager.setEmpname(rs.getNString("manager_name"));
		} catch (SQLException e) {}

		try {
			dept.setDeptName(rs.getString("deptname"));
		} catch (SQLException e) {}

		try {
			dept.setFloor(rs.getInt("floor"));
		} catch (SQLException e) {}

		return new Employee(empno, empname, title, manager, salary, dept);
	}

//	private Employee getEmployeeByTitle(ResultSet rs) throws SQLException {
//		int empno = rs.getInt("empno");
//		String empname = rs.getString("empname");
//
//		return new Employee(empno, empname);
//	}
//
//	private Employee getEmployeeByDepartment(ResultSet rs) throws SQLException {
//		int empno = rs.getInt("empno");
//		String empname = rs.getString("empname");
//
//		return new Employee(empno, empname);
//	}

	// 사원번호로 특정데이터 검색
	@Override
	public Employee selectEmployeeByNo(Employee emp) {
		String sql = "select  empno, empname, title as title_no, manager as manager_no, salary, dept as deptno "
				+ " from employee" + " where empno = ?"; // 변수에 저장
		try (Connection con = JdbcConn.getConnection(); // 1. DB저장
				PreparedStatement pstmt = con.prepareStatement(sql)) { // 2. 쿼리보낼 준비
			pstmt.setInt(1, emp.getEmpno()); // 첫번째 물음표 자리에 사원번호를 반환하는 메소드 호출
			try (ResultSet rs = pstmt.executeQuery()) { // 위 문장을 실행 후 결과값(성공하면 1, 실패하면 0)을 저장
				if (rs.next()) { // 포인터가 가리키는 다음 데이터가 있으면~
					return getEmployee(rs); // 검색한 결과를 반환
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int insertEmployee(Employee emp) {
		String sql = "insert into employee values(?, ?, ?, ?, ?, ?)";
		try (Connection con = JdbcConn.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setInt(1, emp.getEmpno());
			pstmt.setString(2, emp.getEmpname());
			pstmt.setInt(3, emp.getTitle().getTno());
			pstmt.setInt(4, emp.getManager().getEmpno());
			pstmt.setInt(5, emp.getSalary());
			pstmt.setInt(6, emp.getDept().getDeptNo());
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new SqlConstraintException(e.getMessage(), e);
		}
	}

	@Override
	public int updateEmployee(Employee emp) {
		String sql = "update employee set empname = ?, title = ?, manager = ?, salary = ?, dept = ? where empno = ?";
		try (Connection con = JdbcConn.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, emp.getEmpname());
			pstmt.setInt(2, emp.getTitle().getTno());
			pstmt.setInt(3, emp.getManager().getEmpno());
			pstmt.setInt(4, emp.getSalary());
			pstmt.setInt(5, emp.getDept().getDeptNo());
			pstmt.setInt(6, emp.getEmpno());
			return pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

//	@Override
//	public int deleteEmployee(int empno) {
//		String sql = "delete from employee where empno = ?";
//		try (Connection con = JdbcConn.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
//			pstmt.setInt(1, empno);
//			return pstmt.executeUpdate();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return 0;
//	}
	
	@Override
	public int deleteEmployee(Employee emp) {
		String sql = "delete from employee where empno = ?";
		try (Connection con = JdbcConn.getConnection(); 
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, emp.getEmpno());
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	

	@Override
	public List<Employee> selectEmployeeByDeptno(Department dept) {
		String sql = "select empno, empname, title as title_no, manager as manager_no, salary, dept as deptno from employee where dept in (select deptno from department where deptno = ?)";
		try (Connection con = JdbcConn.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, dept.getDeptNo());
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					List<Employee> list = new ArrayList<Employee>();
					do {
						list.add(getEmployee(rs));
					} while (rs.next());
					return list;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Employee> selectEmployeeByTno(Title title) {
		String sql = "select empno, empname from employee e join title t on e.title = t.tno where tno = ?";
		try (Connection con = JdbcConn.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, title.getTno());
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					List<Employee> list = new ArrayList<Employee>();
					do {
						list.add(getEmployee(rs));
					} while (rs.next());
					return list;
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}



}// end of class
