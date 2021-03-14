package erp.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import erp.UI.exception.SqlConstraintException;
import erp.dao.TitleDao;
import erp.database.JdbcConn;
import erp.dto.Title;

//3. TitleDao인터페이스를 구현하는 클래스
public class TitleDaoImpl implements TitleDao {
	// 싱글톤 객체 생성
	private static TitleDaoImpl instance = new TitleDaoImpl();
	
	// 외부에서 객체생성이 불가하도록 private으로 설정해준 기본 생성자
	private TitleDaoImpl() {

	}

	// 생성된 단일 객체의 주소값을 반환하는 메소드
	public static TitleDaoImpl getInstance() {
		return instance;
	}
	
	// Title테이블 안에 있는 모든 데이터 검색
	@Override
	public List<Title> selectTitleByAll() {
		String sql = "select tno,tname from title";		// 모든 데이터를 검색하는 dml문을 문자열타입 변수에 저장
		try (Connection con = JdbcConn.getConnection(); // 1. DB연결
				PreparedStatement pstmt = con.prepareStatement(sql); // 2. 쿼리 날릴 준비를 해라
				ResultSet rs = pstmt.executeQuery()) {	//3. 쿼리를 날리고 그 결과값을 저장
			if (rs.next()) {	// 포인터가 가리키는 곳 다음에 데이터가 있다면 구현부 실행
				List<Title> list = new ArrayList<>();	// 데이터를 담을 List 객체 생성
				do {
					list.add(getTitle(rs));	// getTitle메소드를 호출해서 결과값에 저장되어있는 데이터를 리스트에 저장
				} while (rs.next());	//포인터 다음에 데이터가 존재한다면 구현부 실행(포인터 다음에 데이터가 없을때까지 반복문 실행)
//				System.out.println(list.size());
				return list;	//list를 반환하면 테이블 안에 있는 데이터 전체를 반환한다. 
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;	//포인터 다음에 데이터가 처음부터 없다면 null을 반환한다
	}

	private Title getTitle(ResultSet rs) {// ResultSet에 저장된 결과값을 매개변수로 받는 메소드
		
		int tno = 0;
		String tname = null;
		try {
			tno = rs.getInt("tno");
		}catch(SQLException e) {}
		try {
			tname = rs.getString("tname");
		}catch(SQLException e) {}
		
		return new Title(tno, tname);
	}

	// 직책번호로 특정 데이터 검색
	@Override
	public Title selectTitleByNo(Title title) {
		String sql = "select tno,tname from title where tno = ?";	// 직책번호로 특정데이터 검색하는 dml문을 변수에 저장
		try (Connection con = JdbcConn.getConnection();		//1.DB연결
			PreparedStatement pstmt = con.prepareStatement(sql)) {	//2. 쿼리 날릴 준비
			pstmt.setInt(1, title.getTno());	//첫번째 물음표에 직책번호를 받아오는데 test클래스에서 넣어준 인수를 매개변수로 받아오는 메소드 호출
//			System.out.println(pstmt);
			try (ResultSet rs = pstmt.executeQuery()) {	//위 문장을 수행한 그 결과값을 변수rs에 저장
				if (rs.next()) {	// 포인터가 가리키는 곳 다음에 데이터가 있다면~
					return getTitle(rs);	//그 주소값을 저장하고 있는 변수rs를 매개변수로 가지는 getTitle메소드 호출해서 반환한다.
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// 데이터 삽입
	@Override
	public int insertTitle(Title title) {
		String sql = "insert into title values (?, ?)"; // 데이터를 삽입하는 dml문장을 변수에 저장
		try (Connection con = JdbcConn.getConnection(); 	// 디비연결
			PreparedStatement pstmt = con.prepareStatement(sql)) { // 쿼리날릴 준비
			pstmt.setInt(1, title.getTno());	// 첫번째 물음표에 직책번호를 가져오는 메소드를 호출
			pstmt.setString(2, title.getTname());	// 두번째 물음표에 직책이름을 가져오는 메소드를 호풀
			return pstmt.executeUpdate();	// 위 두문장을 실행하고 그 결과값을 반환한다.(성공하면 1, 실패하면 0)
		} catch (SQLException e) {
			throw new SqlConstraintException(e.getMessage());	
		}
	}

	// 데이터 변경
	@Override
	public int updateTitle(Title title) {
		String sql = "update title set tname = ? where tno = ?";
		try (Connection con = JdbcConn.getConnection(); 
			PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, title.getTname());
			pstmt.setInt(2, title.getTno());
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SqlConstraintException(e.getMessage());
		}
	}
	
	// 데이터 삭제 : 삭제는 객체가 아닌 인스턴스 변수(직책번호)만 있어도 가능하다. 
	@Override
	public int deleteTitle(int titleNo) {
		String sql = "delete from title where tno = ?";	// 데이터를 삭제하는 dml문장을 변수에 저장
		try (Connection con = JdbcConn.getConnection(); // DB연결
			PreparedStatement pstmt = con.prepareStatement(sql)) {// dml문장을 넣고 쿼리 쏴줄 준비
			pstmt.setInt(1, titleNo);	//첫번째 물음표에 직책번호를 바로 넣어줄 수 있다. (해당 메소드의 매개변수를 직책번호로 받아오기때문)
			return pstmt.executeUpdate();	// 위 문장을 실행하고 그 결과값을 반환해준다(성공하면 1, 실패하면 0)
		} catch (SQLException e) {
			throw new SqlConstraintException(e);
//			System.out.println("errorCode : " + e.getErrorCode());	//도달 불가능하다.
//			e.printStackTrace();
		}
//		return 0;
	}

}
