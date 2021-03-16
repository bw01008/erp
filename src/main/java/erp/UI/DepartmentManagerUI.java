package erp.UI;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JOptionPane;

import erp.Service.DepartmentService;
import erp.UI.List.AbstractCustomTablePanel;
import erp.UI.List.DepartmentTablePanel;
import erp.UI.content.AbstractContentPanel;
import erp.UI.content.DeptPanel;
import erp.dto.Department;
import erp.dto.Employee;

@SuppressWarnings("serial")
public class DepartmentManagerUI extends AbstractManagerUI<Department> {
	
	private DepartmentService service;
	
	@Override
	protected void setService() {
		service = new DepartmentService();
		
	}

	@Override
	protected void tableLoadData() {
		((DepartmentTablePanel)pList).setService(service); // 패널도 프레임에 생성된 service 객체를 쓸 수 있게 해준다.
		pList.loadData();
	}

	@Override
	protected AbstractContentPanel<Department> createContentPanel() {
		return new DeptPanel();
	}

	@Override
	protected AbstractCustomTablePanel<Department> createTablePanel() {
		return new DepartmentTablePanel();
	}

	@Override
	protected void actionPerformedMenuGubun() {
		/*
		 * 1. EmployeeDao -> selectEmployeeByTitle() 추가 2. EmployeeDaoImpl ->
		 * selectEmployeeByTitle() 구현 3. EmployeeDaoTest -> Test하기 4. TitleService ->
		 * EmployeeDaoImpl field 추가 및 메서드 추가 5. 아래 기능 추가 6. 예외찾아서 추가하기 (신규 직책 추가 시
		 * NullPointException)
		 */
		Department dept = pList.getItem();
		List<Employee> selectedList = service.showEmployee(dept);
		if (selectedList == null) {
			JOptionPane.showMessageDialog(null, dept.getDeptName() + "부서에 속하는 사원이 존재하지 않습니다.", "동일 부서 사원",
					JOptionPane.INFORMATION_MESSAGE);
			return; // <<return이 왜 나오는지???
		}

		String list = null;
		for (Employee emp : selectedList) {
			list = emp.toString();
		}
		JOptionPane.showMessageDialog(null, list, "해당 부서 사원", JOptionPane.INFORMATION_MESSAGE);
	}

	@Override
	protected void actionPerformedMenuUpdate() {
		Department upDept = pList.getItem();
		pContent.setItem(upDept);
		btnAdd.setText("수정");
	}

	@Override
	protected void actionPerformedMenuDelete() {
		Department delDept = pList.getItem();// 선택된 항목을 객체로 가져온다.
		service.removeDepartment(delDept);// 삭제쿼리문을 동작하는 메소드를 호출해서 데이터를 삭제한다.
		pList.loadData();
		JOptionPane.showMessageDialog(null, delDept.getDeptName() + "부서가 삭제되었습니다.");
	}

	@Override
	protected void actionPerformedBtnUpdate(ActionEvent e) {
		// pContent에서 수정된 title 가져오기
		// update 수행
		// pList 갱신
		// pContent clearTf()호출하여 초기화
		// btnAdd 텍스트 변경 수정->추가
		Department updateDept = pContent.getItem();
		service.modifyDepartment(updateDept);
		pList.loadData();
		pContent.clearTf();
		btnAdd.setText("추가");
		JOptionPane.showMessageDialog(null, "수정이 완료되었습니다.");
	}

	@Override
	protected void actionPerformedBtnAdd(ActionEvent e) {
		Department addDept = pContent.getItem(); // 텍스트필드에 있는 정보로 객체 생성해서 가져온다.
		service.addDepartment(addDept);
		pList.loadData(); // 추가 후 loadData()메소드를 꼭 호출해줘야 반영된다.
		pContent.clearTf();
		JOptionPane.showMessageDialog(null, addDept.getDeptName() + "부서가 추가되었습니다.");
	}

}
