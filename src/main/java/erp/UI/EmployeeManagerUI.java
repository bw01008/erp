package erp.UI;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import erp.Service.EmployeeDetailService;
import erp.Service.EmployeeService;
import erp.UI.List.AbstractCustomTablePanel;
import erp.UI.List.EmployeeTablePanel;
import erp.UI.content.AbstractContentPanel;
import erp.UI.content.EmployeePanel;
import erp.dto.Employee;
import erp.dto.EmployeeDetail;

@SuppressWarnings("serial")
public class EmployeeManagerUI extends AbstractManagerUI<Employee> {

	private EmployeeService service;
	private EmployeeDetailService detailService;

	@Override
	protected void setService() {
		service = new EmployeeService();
		detailService = new EmployeeDetailService();
	}

	public EmployeeManagerUI() {
		empListByTitleItem.setText(AbstractManagerUI.EMP_MENU);		//상위클래스에 선언된 상수 호출

	}

	@Override
	protected void tableLoadData() {
		// 패널도 프레임에 생성된 service 객체를 쓸 수 있게 해준다.
		((EmployeeTablePanel) pList).setService(service);
		// 데이터를 읽어와서 리스트 > 배열 > 모델로 만들어서 테이블에 달아주는 메소드를 pList에 호출하게 되면 UI에 데이터가 반영된다.
		pList.loadData();
	}

	@Override
	protected AbstractContentPanel<Employee> createContentPanel() {
		EmployeePanel EmpPanel = new EmployeePanel();
		EmpPanel.setService(service); // 패널에도 서비스가 필요하기 떄문에
		return EmpPanel;
	}

	@Override
	protected AbstractCustomTablePanel<Employee> createTablePanel() {
		return new EmployeeTablePanel();
	}

	@Override
	// 사원 상세 정보보기 메뉴 선택할때 동작하는 메소드
	protected void actionPerformedMenuGubun() {
		//테이블에서 선택된 Employee 가져오기
		Employee emp = pList.getItem();
//		System.out.println(emp);	//잘 넘어오는지 확인
		
		//select 수행
		EmployeeDetail empDetail = detailService.selectEmployeeDetailByEmpNo(emp);
//		System.out.println(empDetail);	//사번1003만 넘어오고 나머지는 null > 상세정보 추가하시겠습니까? 기능 추가필요
		
		// 나중에 처리
		EmployeeDetailUI frame;
		//선택된 Employee에 대한 상세정보가 없으면 추가 기능있는 창이 뜬다.
		if (empDetail == null) {
			frame = new EmployeeDetailUI(true, detailService);
//			JOptionPane.showMessageDialog(null, "세부정보 없음");
//			return; >> 바로 밖으로 나가기 때문에 아래 코드를 수행하지 않는다 > setVisible(true); 안됨
		} else {
		//선택된 Employee에 대한 상세정보가 있으면 창을 띄워준다.(수정/삭제가능)
			frame = new EmployeeDetailUI(false, detailService);
			frame.setDetailItem(empDetail);
		}
		//empNo 텍스트 필드에 선택된 Employee의 empNo로 세팅해준다.
		frame.setEmpNo(emp);
		frame.setVisible(true);
		
		/*
		 * JFrame subFrame = new JFrame("사원 세부 정보");
		 * 
		 * subFrame.setBounds(this.getWidth(), this.getHeight(), 450, 500);
		 * EmployeeDetailPanel subDetailPanel = new EmployeeDetailPanel();
		 * 
		 * subDetailPanel.setItem(empDetail);
		 * 
		 * subFrame.add(subDetailPanel, BorderLayout.CENTER);
		 * 
		 * subFrame.setVisible(true);
		 */

	}

	@Override
	protected void actionPerformedMenuUpdate() {
		// 1. 선택된 Employee 가져오기
		// 2. 선택된 객체를 pContent에 수정할 수 있도록 가져오기
		// 3. 추가버튼을 수정버튼으로 바꾼다.
		Employee upEmp = pList.getItem();
		pContent.setItem(upEmp);
		btnAdd.setText("수정");

	}

	@Override
	protected void actionPerformedMenuDelete() {
		//1. 선택된 Employee 가져오기
		//2. delete수행
		//3. pList 갱신
		Employee delEmp = pList.getItem();
		service.removeEmployee(delEmp);
		pList.loadData();
		JOptionPane.showMessageDialog(null, delEmp.getEmpname() + "이/가 삭제되었습니다.");

	}

	@Override
	protected void actionPerformedBtnUpdate(ActionEvent e) {
		//1. pContent에서 수정된 employee 가져오기
		//2. update수행
		//3. pList 갱신
		//4. pContent clearTf()호출해서 초기화
		Employee updateEmp = pContent.getItem();
		service.modifyEmployee(updateEmp);
		pList.loadData();
		pContent.clearTf();
		btnAdd.setText("추가");
		JOptionPane.showMessageDialog(null, "수정이 완료되었습니다.");

	}

	@Override
	protected void actionPerformedBtnAdd(ActionEvent e) {
		//1. pContent에서 작성된 employee 가져오기 
		//2. insert(add)수행
		//3. pList 갱신
		//4. pContent clearTf()호출해서 초기화
		Employee addEmp = pContent.getItem();
		service.addEmployee(addEmp);
		pList.loadData();
		pContent.clearTf();
		JOptionPane.showMessageDialog(null, addEmp.getEmpname() + " 추가되었습니다.");

	}

}
