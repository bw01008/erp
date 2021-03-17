package erp.UI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import erp.Service.EmployeeDetailService;
import erp.Service.EmployeeService;
import erp.UI.List.AbstractCustomTablePanel;
import erp.UI.List.EmployeeTablePanel;
import erp.UI.content.AbstractContentPanel;
import erp.UI.content.EmployeeDetailPanel;
import erp.UI.content.EmployeePanel;
import erp.dto.Employee;
import erp.dto.EmployeeDetail;

@SuppressWarnings("serial")
public class EmployeeManagerUI extends AbstractManagerUI<Employee> {
	
	private EmployeeService service;
	private EmployeeDetailService detailService;
	
	
	public EmployeeManagerUI() {
		empListByTitleItem.setText(AbstractManagerUI.EMP_MENU);
		
	}

	@Override
	protected void setService() {
		service = new EmployeeService();
		detailService = new EmployeeDetailService();
	}

	@Override
	protected void tableLoadData() {
		((EmployeeTablePanel)pList).setService(service); // 패널도 프레임에 생성된 service 객체를 쓸 수 있게 해준다.
		pList.loadData();
	}

	@Override
	protected AbstractContentPanel<Employee> createContentPanel() {
		EmployeePanel EmpPanel = new EmployeePanel();
		EmpPanel.setService(service);	//패널에도 서비스가 필요하기 떄문에 
		return EmpPanel;
	}

	@Override
	protected AbstractCustomTablePanel<Employee> createTablePanel() {
		return new EmployeeTablePanel();
	}

	@Override
	protected void actionPerformedMenuGubun() {
		// 사원 상세 정보보기 메뉴 선택할때 동작하는 코드
		Employee emp = pList.getItem();
//		System.out.println(emp);	//잘 넘어오는지 확인
		EmployeeDetail empDetail = detailService.selectEmployeeDetailByEmpNo(emp);
//		System.out.println(empDetail);	//사번1003만 넘어오고 나머지는 null > 상세정보 추가하시겠습니까? 기능 추가필요
		if(empDetail==null) {
			JOptionPane.showMessageDialog(null, "세부정보 없음");
			return;
		}
		
		JFrame subFrame = new JFrame("사원 세부 정보");
		
		subFrame.setBounds(this.getWidth(), this.getHeight(), 450, 500);
		EmployeeDetailPanel subDetailPanel = new EmployeeDetailPanel();
		
		subDetailPanel.setItem(empDetail);
		
		subFrame.add(subDetailPanel, BorderLayout.CENTER);
		
		subFrame.setVisible(true);
		
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
