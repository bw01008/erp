package erp.dao;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import erp.daoImpl.TitleDaoImpl;
import erp.dto.Title;
//4. 데이터 연동이 잘되었는지 단위테스트
@FixMethodOrder(MethodSorters.NAME_ASCENDING)	// 이름 순서대로 실행하라 > 메소드이름에 넘버링
public class TitleDaoTest {
	// 연동을 실행한 객체의 주소값을 얻어오는 메소드를 호출해서 그 주소값을 dao에 저장
	private static TitleDao dao = TitleDaoImpl.getInstance();

	@After // 4. testGetConnection()가 수행되기 전에 호출된다
	public void tearDown() throws Exception {
		System.out.println();
	}

	// 테이블 안에 있는 데이터 전체 검색이 되는지 테스트하는 메소드
	@Test
	public void test04SelectTitleByAll() {
		System.out.printf("%s()%n", "testSelectTitleByAll");
		List<Title> titleList = dao.selectTitleByAll();	// List객체에 실행된 메소드의 결과(list가 반환된다)를 List에 담는다.
		Assert.assertNotNull(titleList); // 그 객체가 비어있지 않은지 확인 > 비어있지 않다면 true > 성공

//		titleList.stream().forEach(System.out::println);
		for (Title t : titleList) {		//리스트 안에 있는 데이터들을 하나씩 꺼내어 출력
			System.out.println(t);
		}
	}
	
	
	//직책번호로 특정 데이터를 검색이 되는지 테스트하는 메소드
	@Test
	public void test05SelectTitleByNo() {
		System.out.printf("%s()%n", "testSelectTitleByNo");
		Title title = new Title(3);		//직책번호를 매개변수로 생성자 > 객체 생성
		Title searchTitle = dao.selectTitleByNo(title);	// 생성한 객체를 매개변수로 넣어 메소드 호출(실행)후 그 결과값을 저장
		Assert.assertNotNull(searchTitle); // 결과값이 저장된 객체 안이 비어있는지 확인 > 비어있지않다면 true > 성공
		System.out.println(searchTitle);	// 3번으로 검색된 데이터 출력
	}
	
	// 데이터 추가가 잘되었는지 테스트하는 메소드
	@Test
	public void test01InsertTitle() {
		System.out.printf("%s()%n", "testInsertTitle");	
		Title newTitle = new Title(6, "인턴");	//추가할 데이터가 담긴 객체 생성
		int res = dao.insertTitle(newTitle);	// 해당 객체를 매개변수로 넘겨서 데이터 추가하는 메소드를 호출(실행)하고 그 결과값을 받아서(성공하면 1, 실패하면 0) 변수에 저장
		Assert.assertEquals(1, res);		// 결과값이 1인지 비교한다. 1이면 성공, 0이면 실패
		System.out.println(dao.selectTitleByNo(newTitle)); // 추가된 결과를 출력한다.
	}
	
	// 
	@Test
	public void test02UpdateTitle() {
		System.out.printf("%s()%n", "testUpdateTitle");
		Title newTitle = new Title(6, "계약직");
		int res = dao.updateTitle(newTitle);
		Assert.assertEquals(1, res);
		System.out.println(dao.selectTitleByNo(newTitle));
	}

	// 데이터 삭제가 잘되었는지 테스트하는 메소드(직책번호만 가지고 삭제가 가능하다)
	@Test
	public void test03DeleteTitle() {
		System.out.printf("%s()%n", "testDeleteTitle");
		int res = dao.deleteTitle(6);	// 직책번호를 매개변수로 메소드 호출하고 그 결과값을 받아서 (성공 1, 실패 0) 변수에 저장
		Assert.assertEquals(1, res);	// 그 결과값이 1인지 비교한다. 1이면 성공, 0이면 실패
		dao.selectTitleByAll().stream().forEach(System.out::println);
	}

}
