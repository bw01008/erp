package erp.dao;

import java.util.List;

import erp.dto.Department;
import erp.dto.Employee;
import erp.dto.Title;

// 2. Employee를 구현할 인터페이스
public interface EmployeeDao {
	// 검색된 모든 데이터를 담을 List 생성
	List<Employee> selectEmployeeByAll();
	// 사원번호로 검색된 데이터를 담을 객체 생성
	Employee selectEmployeeByNo(Employee emp);
	// insert 추상메서드
	int insertEmployee(Employee emp);
	// update 추상메서드
	int updateEmployee(Employee emp);
	// delete 추상메서드
	int deleteEmployee(int empno);
	
	//해당 부서에 속하는 사원목록을 검색
	List<Employee> selectEmployeeByDeptno(Department dept);
	
	//해당직책을 가지고 있는 사원목록을 검색
	List<Employee> selectEmployeeByTno(Title title);
	
	
	
	
	
}
