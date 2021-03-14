package erp.dao;
//2. Title을 구현할 인터페이스
import java.util.List;

import erp.dto.Title;

/**
 * C(insert) 
 * R(select, select where)
 * U(update) 
 * D(delete)
 */

public interface TitleDao {
	// 1. 테이블 안에 있는 모든 데이터를 검색해서 담을 List생성
	List<Title> selectTitleByAll();
	// 2. 직책번호를 통해 특정 데이터를 검색할 추상메서드
	Title selectTitleByNo(Title title);
	// 3. insert추상메서드 : 추가할 데이터를 객체로 받는다
	int insertTitle(Title title);
	// 4. update추상메서드 : 변경할 데이터를 객체로 받는다.
	int updateTitle(Title title);
	// 5. delete추상메서드 : 번호만 알면 삭제할 수 있기 때문에 번호를 매개변수로 받는다.
	int deleteTitle(int titleNo);

}






