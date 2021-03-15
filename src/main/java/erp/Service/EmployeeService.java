package erp.Service;

import java.util.List;

import erp.dao.DepartmentDao;
import erp.dao.EmployeeDao;
import erp.dao.TitleDao;
import erp.daoImpl.DepartmentDaoImpl;
import erp.daoImpl.EmployeeDaoImpl;
import erp.daoImpl.TitleDaoImpl;
import erp.dto.Department;
import erp.dto.Employee;
import erp.dto.Title;

public class EmployeeService {
	private DepartmentDao deptDao = DepartmentDaoImpl.getInstnace();
	private TitleDao titleDao = TitleDaoImpl.getInstance();
	private EmployeeDao empDao = EmployeeDaoImpl.getInstance();
	
	public List<Department> showDeptList(){
		return deptDao.selectDepartmentByAll();
	}
	
	public List<Title> showTitleList(){
		return titleDao.selectTitleByAll();
	}
	
	//부서별 사원
	public List<Employee> showEmployeeListByDept(Department dept){
		return empDao.selectEmployeeByDeptno(dept);
	}
	
	public List<Employee> showEmployeeList(){
		List<Employee> list = empDao.selectEmployeeByAll();
//		System.out.println(list);
		return list;
	}
	
	public void addEmployee(Employee emp) {
		empDao.insertEmployee(emp);
	}

	
	
}
