package erp.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import erp.UI.exception.SqlConstraintException;
import erp.dao.EmployeeDetailDao;
import erp.database.JdbcConn;
import erp.dto.Employee;
import erp.dto.EmployeeDetail;

public class EmployeeDetailDaoImpl implements EmployeeDetailDao {
	private static EmployeeDetailDaoImpl instance = new EmployeeDetailDaoImpl();

	private EmployeeDetailDaoImpl() {
	}

	public static EmployeeDetailDaoImpl getInstance() {
		if (instance == null) {
			instance = new EmployeeDetailDaoImpl();
		}
		return instance;
	}

	@Override
	public EmployeeDetail selectEmployeeDatailByNo(Employee employee) {
		//pass는 검색하지 않는다. > 보안을 위해
		String sql = "select empno, pic, gender, hiredate from emp_detail where empno = ?";
		try (Connection con = JdbcConn.getConnection(); //
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, employee.getEmpno());
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return getEmployeeDetail(rs);	//쿼리문 실행 결과를 객체로 반환한다.
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private EmployeeDetail getEmployeeDetail(ResultSet rs) throws SQLException {
		int empNo = rs.getInt("empno");
		boolean gender = rs.getBoolean("gender");
		Date hireDate = rs.getTimestamp("hiredate");
		byte[] pic = rs.getBytes("pic");
		return new EmployeeDetail(empNo, gender, hireDate, pic);
	}

	@Override
	public int insertEmployeeDetail(EmployeeDetail empDetail) {
		// 쿼리문 작성시 비밀번호의 경우 ?를 password(?)로 한번 감싸주어야한다.
		String sql = "insert into emp_detail (empno, pic, gender, hiredate, pass) values (?, ?, ?, ?, password(?))";
		try (Connection con = JdbcConn.getConnection(); 
				PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setInt(1, empDetail.getEmpNo());
			pstmt.setBytes(2, empDetail.getPic());
			pstmt.setBoolean(3, empDetail.isGender());
			// util.Date > sql.Date로 변환
			pstmt.setTimestamp(4, new Timestamp(empDetail.getHireDate().getTime())); // 초로 받아와서
			pstmt.setString(5, empDetail.getPass());
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new SqlConstraintException(e.getMessage(), e);
		}
	}

	@Override
	public int updateEmployeeDetail(EmployeeDetail empDetail) {
		String sql = "update emp_detail set pic = ?, gender = ?, hiredate = ?, pass=password(?)  where empno = ?";
		try (Connection con = JdbcConn.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setBytes(1, empDetail.getPic());
			pstmt.setBoolean(2, empDetail.isGender());
			pstmt.setTimestamp(3, new Timestamp(empDetail.getHireDate().getTime()));
			pstmt.setString(4, empDetail.getPass());
			pstmt.setInt(5, empDetail.getEmpNo());
			return pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int deleteEmployeeDetail(Employee employee) {
		String sql = "delete from emp_detail where empno = ?";
		try (Connection con = JdbcConn.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, employee.getEmpno());
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
