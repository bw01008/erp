package erp.Service;

import java.util.List;

import erp.dao.EmployeeDao;
import erp.dao.TitleDao;
import erp.daoImpl.EmployeeDaoImpl;
import erp.daoImpl.TitleDaoImpl;
import erp.dto.Employee;
import erp.dto.Title;
//dao와 UI를 연결해주는 역할을 하는 service클래스를 만든다.(UI와 DB연결)
public class TitleService {
	private TitleDao dao = TitleDaoImpl.getInstance();	//메소드들에 접근
	private EmployeeDao daoEmp = EmployeeDaoImpl.getInstance();
	
//	public List<Title> showTitles(){
//		List<Title> list = dao.selectTitleByAll();
//		System.out.println(list);
//		return list;
//	}
	
	public void addTitle(Title title) {
		dao.insertTitle(title);
	}
	
	public void removeTitle(Title title) {
		dao.deleteTitle(title.getTno());
	}
	
	public void modifyTitle(Title title) {
		dao.updateTitle(title);
	}
	
	public List<Employee> showEmployee(Title title) {
		return daoEmp.selectEmployeeByTno(title);
	}

	public List<Title> showTitle(){
		return dao.selectTitleByAll();
	}
	
	
}// end of class
