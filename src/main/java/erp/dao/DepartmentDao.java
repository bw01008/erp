package erp.dao;

import java.util.List;

import erp.dto.Department;
// 2. department를 구현할 인터페이스
public interface DepartmentDao {
	/**	
	 * C(insert) 
	 * R(select, select where)
	 * U(update) 
	 * D(delete)
	 */
	
	// 테이블 안에 있는 모든 데이터를 검색할 List생성
	List<Department> selectDepartmentByAll();
	// 부서번호로 검색할 추상메소드
	Department selectDepartmentByNo(Department dept);
	// insert추상메소드
	int insertDepartment(Department dept);
	//update추상메소드
	int updateDepartment(Department dept);
	//delete추상메소드 
	int deleteDepartment(int deptNo);
	// 

	
	
}
