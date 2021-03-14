package erp.dao;

//4. 데이터가 잘 연동되었는지 확인하는 단위테스트
import java.util.List;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import erp.daoImpl.EmployeeDaoImpl;
import erp.dto.Department;
import erp.dto.Employee;
import erp.dto.Title;

@FixMethodOrder(MethodSorters.NAME_ASCENDING) // 이름 순서대로 실행하라 > 메소드 이름 넘버링
public class EmployeeDaoTest {
	// 객체 주소를 받아오는 메소드를 호출해서 그 반환값을 저장
	private static EmployeeDao dao = EmployeeDaoImpl.getInstance();

	// 테이블 안에 있는 전체 데이터가 검색되는지 테스트
	@Test
	public void test04SelectEmployeeByAll() {
		System.out.printf("%s()%n", "test04SelectEmployeeByAll");
		List<Employee> EmployeeList = dao.selectEmployeeByAll();// 검색된 결과를 List에 저장
		Assert.assertNotNull(EmployeeList);

		for (Employee e : EmployeeList) {
			System.out.println(e);
		}
	}

	@Test
	public void test05SelectEmployeeByNo() {
		System.out.printf("%s()%n", "test05SelectEmployeeByNo");
		Employee emp = new Employee(4377);
		Employee searchEmp = dao.selectEmployeeByNo(emp);
		Assert.assertNotNull(searchEmp);
		System.out.println(searchEmp);
	}
	
	@Test
	public void test06selectEmployeeByDeptno() {
		System.out.printf("%s()%n", "test06selectEmployeeByDeptno");
		Department dept = new Department(1);
		List<Employee> EmployeeList = dao.selectEmployeeByDeptno(dept);
		Assert.assertNotNull(EmployeeList);

		for (Employee e : EmployeeList) {
			System.out.println(e);
		}
	}
	
	public void Test07SelectEmployeeByTno() {
		System.out.printf("%s()%n", "Test07SelectEmployeeByTno");
		Title title = new Title(1);
		List<Employee> EmployeeList = dao.selectEmployeeByTno(title);
		Assert.assertNotNull(EmployeeList);
	}
	

	
	@Test
	public void test01InsertEmployee() {
		System.out.printf("%s()%n", "test01InsertEmployee");
		Employee newEmp = new Employee(1004, "천사", new Title(5), new Employee(4377), 2000000, new Department(1));
		int res = dao.insertEmployee(newEmp);
		Assert.assertEquals(1, res);
		System.out.println(dao.selectEmployeeByNo(newEmp));
	}

	@Test
	public void test02UpdateEmployee() {
		System.out.printf("%s()%n", "test02UpdateEmployee");
		Employee newEmp = new Employee(1004, "천사2", new Title(4), new Employee(1003), 2000000, new Department(3));
		int res = dao.updateEmployee(newEmp);
		Assert.assertEquals(1, res);
		System.out.println(dao.selectEmployeeByNo(newEmp));
	}

	@Test
	public void test03DeleteEmployee() {
		System.out.printf("%s()%n", "test03DeleteEmployee");
		int res = dao.deleteEmployee(1004);
		Assert.assertEquals(1, res);
		dao.selectEmployeeByAll().stream().forEach(System.out::println);
	}

}