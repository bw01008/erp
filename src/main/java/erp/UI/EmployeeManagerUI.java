package erp.UI;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import erp.Service.EmployeeService;
import erp.UI.List.AbstractCustomTablePanel;
import erp.UI.List.EmployeeTablePanel;
import erp.UI.content.AbstractContentPanel;
import erp.UI.content.EmpPanel;
import erp.dto.Employee;

@SuppressWarnings("serial")
public class EmployeeManagerUI extends AbstractManagerUI<Employee> {
	
	private EmployeeService service;

	@Override
	protected void setService() {
		service = new EmployeeService();
		
	}

	@Override
	protected void tableLoadData() {
		((EmployeeTablePanel)pList).setService(service); // 패널도 프레임에 생성된 service 객체를 쓸 수 있게 해준다.
		pList.loadData();
	}

	@Override
	protected AbstractContentPanel<Employee> createContentPanel() {
		EmpPanel EmpPanel = new EmpPanel();
		EmpPanel.setService(service);	//패널에도 서비스가 필요하기 떄문에 
		return EmpPanel;
	}

	@Override
	protected AbstractCustomTablePanel<Employee> createTablePanel() {
		return new EmployeeTablePanel();
	}

	@Override
	protected void actionPerformedMenuGubun() {
		//사원관리에서 따로 제공하지 않는 기능이다.(필요없음)
		throw new UnsupportedOperationException("제공되지 않음");
		
	}

	@Override
	protected void actionPerformedMenuUpdate() {
		Employee upEmp = pList.getItem();
		pContent.setItem(upEmp);
		btnAdd.setText("수정");
		
	}

	@Override
	protected void actionPerformedMenuDelete() {
		Employee delEmp = pList.getItem();// 선택된 항목을 객체로 가져온다.
		service.removeEmployee(delEmp);// 삭제쿼리문을 동작하는 메소드를 호출해서 데이터를 삭제한다.
		pList.loadData();
		JOptionPane.showMessageDialog(null, delEmp.getEmpname() + "이/가 삭제되었습니다.");
		
	}

	@Override
	protected void actionPerformedBtnUpdate(ActionEvent e) {
		Employee updateEmp = pContent.getItem();
		service.modifyEmployee(updateEmp);
		pList.loadData();
		pContent.clearTf();
		btnAdd.setText("추가");
		JOptionPane.showMessageDialog(null, "수정이 완료되었습니다.");
		
	}

	@Override
	protected void actionPerformedBtnAdd(ActionEvent e) {
		Employee addEmp = pContent.getItem(); // 텍스트필드에 있는 정보로 객체 생성해서 가져온다.
		service.addEmployee(addEmp);
		pList.loadData(); // 추가 후 loadData()메소드를 꼭 호출해줘야 반영된다.
		pContent.clearTf();
		JOptionPane.showMessageDialog(null, addEmp.getEmpname() + "이/가 추가되었습니다.");
		
	}

}
