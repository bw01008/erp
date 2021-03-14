package erp.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import erp.daoImpl.DepartmentDaoImpl;
import erp.dto.Department;
//4. 데이터 연동이 잘되었는지 단위테스트
@FixMethodOrder(MethodSorters.NAME_ASCENDING)	// 이름 순서대로 테스트를 실행하라 > 메소드들마다 넘버링해주면 된다.
public class DepartmentDaoTest {
	//객체주소를 받아오는 메소드를 호출해서 그 반환값을 저장
	private static DepartmentDao dao = DepartmentDaoImpl.getInstnace();

	// 테이블에 데이터 전체를 검색
	@Test
	public void test04SelectDepartmentByAll() {
		System.out.printf("%s()%n", "testSelectDepartmentByAll");	//진행 순서를 편하게 보기위해 찍어보는 문장
		List<Department> DepartmentList = dao.selectDepartmentByAll(); //객체 안에서 실행한 메소드의 결과들을 List 객체에 담는다...?
		Assert.assertNotNull(DepartmentList); //List안이 비어있지 않은지 확인 > 비어있지않으면 true > 성공
	}

	// 테이블에 특정 부서번호로 데이터 검색
	@Test
	public void test05SelectDepartmentByNo() {
		System.out.printf("%s()%n", "testSelectDepartmentByNo");
		Department dept = new Department(1);	//부서번호를 매개변수로 받는 생성자로 객체 생성
		Department searchDept = dao.selectDepartmentByNo(dept);	//DepartmentDaoImpl객체 안에서 실행한 메소드 결과를 Department객체에 복사
		Assert.assertNotNull(searchDept);	//객체 안이 비어있지 않은지 확인 > 비어있지 않으면 true > 성공
		System.out.println(searchDept);
	}

	// 테이블에 데이터 삽입
	@Test
	public void test01InsertDepartment() {
		System.out.printf("%s()%n", "testInsertDepartment");
		Department newDept = new Department(5, "디자인", 20); //Department객체 생성
		int res = dao.insertDepartment(newDept);	//DepartmentDaoImpl객체 안에서 실행한 메소드 결과(성공하면 1, 실패하면 0)를 정수타입 변수 res에 저장
		Assert.assertEquals(1, res);	// 만약 그 결과값이 1과 같은지 검사 > 같으면 성공
		System.out.println(dao.selectDepartmentByNo(newDept)); // 실행성공한 객체를 출력
	}

	//테이블에 데이터 변경
	@Test
	public void test02UpdateDepartment() {
		System.out.printf("%s()%n", "test02UpdateDepartment");
		Department newDept = new Department(4, "마케팅", 7); //변경할 데이터를 가진 객체를 새로 생성
		int res = dao.updateDepartment(newDept); // DepartmentDaoImpl객체 안에서 실행한 메소드 결과(성공하면 1, 실패하면 0)을 정수타입 변수 res에 저장
		Assert.assertEquals(1, res); // 만약 그 결과가 1과 같은지 검사 > 같으면 성공
		System.out.println(dao.selectDepartmentByNo(newDept)); // 실행성공한 객체(결과)를 출력
		
	}

	// 테이블에 데이터 삭제
	@Test
	public void test03DeleteDepartment() {
		System.out.printf("%s()%n", "test03DeleteDepartment");
		int res = dao.deleteDepartment(5);	//부서번호를 매개변수로 받는 메소드를 호출해서 그 결과(성공하면 1, 실패하면 0)를 반환받아 정수타입 변수 res에 저장
		Assert.assertEquals(1, res); // 만약 그 결과값이 1과 같은지 검사 > 같으면 성공
		
	}

}
