package erp.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insertEmployeeDetail(EmployeeDetail empDetail) {
		//쿼리문 작성시 비밀번호의 경우 ?를 password(?)로 한번 감싸주어야한다.
		String sql = "INSERT INTO emp_detail (empno, pic, gender, hiredate, pass) VALUES(?, ?, ?, ?, password(?))";
		try (Connection con = JdbcConn.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setInt(1, empDetail.getEmpNo());
			pstmt.setBytes(2, empDetail.getPic());
			pstmt.setBoolean(3, empDetail.isGender());
			//util.Date > sql.Date로 변환
			pstmt.setTimestamp(4, new Timestamp(empDetail.getHireDate().getTime()));	//초로 받아와서 
			pstmt.setString(5, empDetail.getPass());
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new SqlConstraintException(e.getMessage(), e);
		}
	}

	@Override
	public int updateEmployeeDetail(EmployeeDetail empDetail) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteEmployeeDetail(EmployeeDetail empDetail) {
		// TODO Auto-generated method stub
		return 0;
	}

}
