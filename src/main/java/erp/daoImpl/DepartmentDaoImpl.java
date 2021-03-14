package erp.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import erp.dao.DepartmentDao;
import erp.database.JdbcConn;
import erp.dto.Department;

// 3. Department 인터페이스를 구현하는 클래스
public class DepartmentDaoImpl implements DepartmentDao {
	// 싱글톤 객체 생성
	private static DepartmentDaoImpl instance = new DepartmentDaoImpl();

	// 외부에서 객체 생성이 불가하도록 private으로 설정한 기본 생성자
	private DepartmentDaoImpl() {
	}

	// 객체 주소값 반환
	public static DepartmentDaoImpl getInstnace() {
		return instance;
	}

	// Department 테이블 안에 있는 데이터 전체 검색
	@Override
	public List<Department> selectDepartmentByAll() {
		String sql = "select deptNo,deptName,floor from department"; // 테이블 안에 있는 데이터 전체 검색 dml문을 변수에 저장
		try (Connection con = JdbcConn.getConnection(); // 1. DB와 연결
				PreparedStatement pstmt = con.prepareStatement(sql); // 2. 쿼리날릴 준비
				ResultSet rs = pstmt.executeQuery()) { // 3. 쿼리 날리고 결과를 변수에 저장
			if (rs.next()) { // 포인터 다음에 데이터가 존재한다면 구현부 실행
				List<Department> list = new ArrayList<>(); // 데이터들을 담을 ArrayList 객체 생성
				do {
					list.add(getDepartment(rs)); // ArrayList에 데이터를 추가
				} while (rs.next()); // 담은 데이터 다음에 또 데이터가 존재하면 반복문 실행해서 List 맨끝에 데이터 추가
				return list; // list를 반환하면 테이블 안에 있는 데이터 전체를 반환한다.
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Department getDepartment(ResultSet rs) {
		int deptNo = 0;
		String deptName = null;
		int floor = 0;
		
		try {
			deptNo = rs.getInt("deptNo");
		}catch(SQLException e) {}
		
		try {
			deptName = rs.getString("deptName");
		}catch(SQLException e) {}
		try {
			floor = rs.getInt("floor");
		}catch(SQLException e) {}
		
		return new Department(deptNo, deptName, floor);
	}

	// 부서번호로 원하는 데이터 검색
	@Override
	public Department selectDepartmentByNo(Department dept) {
		String sql = "select deptNo,deptName,floor from department where deptNo = ?"; // 부서번호를 통해 원하는 데이터를 검색할 dml문을 변수에
																						// 저장
		try (Connection con = JdbcConn.getConnection(); // 1. DB와 연결
				PreparedStatement pstmt = con.prepareStatement(sql)) { // 2. 쿼리 날릴 준비
			pstmt.setInt(1, dept.getDeptNo()); // 첫번째 ?에 부서번호를 넣는데 test클래스에서 넣어준 인수를 매개변수로 받은 메소드 호출(메소드는 인스턴스 변수를 가져온다)
			try (ResultSet rs = pstmt.executeQuery()) { // 위 문장을(쿼리를) 올려주고 그 결과(성공하면 1, 실패하면 0)를 변수 rs에 저장
				if (rs.next()) { // 포인터가 가리키는 데이터가 있으면~
					return getDepartment(rs); // 검색결과를 반환
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	// 테이블에 데이터 추가
	@Override
	public int insertDepartment(Department dept) {
		String sql = "insert into department values (?, ?, ?)"; // 해당테이블에 데이터를 추가해줄 dml문을 변수에 저장
		try (Connection con = JdbcConn.getConnection(); // 1. DB와 연결
				PreparedStatement pstmt = con.prepareStatement(sql)) { // 2. 쿼리 날릴 준비
			pstmt.setInt(1, dept.getDeptNo()); // 첫번째 물음표에 부서번호를 넣는데 test클래스에서 넣어준 인수를 매개변수로 받는 메소드 호출(메소드는 인스턴스 변수값을
												// 받아온다)
			pstmt.setString(2, dept.getDeptName()); // 두번째 물음표에 부서이름을 가지고오는 메소드 호출
			pstmt.setInt(3, dept.getFloor()); // 세번째 물음표에 부서위치를 가지고오는 메소드 호출
			return pstmt.executeUpdate(); // 위 코드 3줄을 실행해서 올린 결과를 반환(deptno, deptname, floor)가 추가 된다.

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	// 테이블에 있는 데이터 수정/변경
	@Override
	public int updateDepartment(Department dept) {
		String sql = "update department set deptName = ?, floor = ? where deptNo = ?"; // 해당테이블에 특정데이터를 변경해줄 dml을 변수에 저장
		try (Connection con = JdbcConn.getConnection(); // 1. DB와 연결
				PreparedStatement pstmt = con.prepareStatement(sql)) { // 2. 쿼리 날릴 준비
			pstmt.setString(1, dept.getDeptName()); // 첫번째 물음표에 변경할 부서이름을 가지고 오는 메소드 호출
			pstmt.setInt(2, dept.getFloor());
			pstmt.setInt(3, dept.getDeptNo()); // 두번째 물음표에 변경될 부서번호를 가지고오는 메소드 호출
			return pstmt.executeUpdate(); // 위 코드 2줄을 실행해서 올린 결과를 반환
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	// 테이블에 있는 특정 데이터 삭제
	@Override
	public int deleteDepartment(int deptNo) {
		String sql = "delete from department where deptNo = ?"; // 해당 테이블에서 특정 데이터를 삭제할 dml을 변수에 저장
		try (Connection con = JdbcConn.getConnection(); // 2. DB와 연결
				PreparedStatement pstmt = con.prepareStatement(sql)) { // 2. 쿼리 날릴 준비
			pstmt.setInt(1, deptNo); // 물음표에 부서번호를 바로 넣어줄 수 있다.(해당 메소드의 배개변수가 int deptNo이기때문)
			return pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

}
