package erp.Service;

import java.util.List;

import erp.dao.DepartmentDao;
import erp.dao.EmployeeDao;
import erp.daoImpl.DepartmentDaoImpl;
import erp.daoImpl.EmployeeDaoImpl;
import erp.dto.Department;
import erp.dto.Employee;

public class DepartmentService {
	// 인터페이스로 구현클래스의 메소드에 접근하도록 선언
	private DepartmentDao dao = DepartmentDaoImpl.getInstnace();
	private EmployeeDao empDao = EmployeeDaoImpl.getInstance();
	
	// 메소드에 접근해서 List를 받아오는 메소드 생성
	public List<Department> showDepartment(){
		return dao.selectDepartmentByAll();
	}
	
	public void removeDepartment(Department dept) {
		dao.deleteDepartment(dept.getDeptNo());
	}
	
	public void addDepartment(Department dept) {
		dao.insertDepartment(dept);
	}
	
	public void modifyDepartment(Department dept) {
		dao.updateDepartment(dept);
	}
	
	public List<Employee> showEmployeeGroupByDepartment(Department dept){
		return empDao.selectEmployeeByDeptno(dept);
	}

}
