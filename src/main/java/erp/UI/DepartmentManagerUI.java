package erp.UI;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import erp.Service.DepartmentService;
import erp.UI.List.AbstractCustomTablePanel;
import erp.UI.List.DepartmentTablePanel;
import erp.UI.content.AbstractContentPanel;
import erp.UI.content.DepartmentPanel;
import erp.dto.Department;
import erp.dto.Employee;

@SuppressWarnings("serial")
public class DepartmentManagerUI extends AbstractManagerUI<Department> {

	private DepartmentService service;

	@Override
	protected void setService() {
		service = new DepartmentService();
	}
	
	public DepartmentManagerUI() {
		empListByTitleItem.setText(AbstractManagerUI.DEPT_MENU);	//상위클래스에 선언된 상수 호출
	}

	@Override
	protected void tableLoadData() {
		// 패널도 프레임에 생성된 service 객체를 쓸 수 있게 해준다.
		((DepartmentTablePanel)pList).setService(service);
		// 데이터를 읽어와서 리스트 > 배열 > 모델로 만들어서 테이블에 달아주는 메소드를 pList에 호출하게 되면 UI에 데이터가 반영된다.
		pList.loadData();
	}

	@Override
	protected AbstractContentPanel<Department> createContentPanel() {
		return new DepartmentPanel();
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
		List<Employee> selectedList = service.showEmployeeGroupByDepartment(dept);
		
		if (selectedList == null) {
			JOptionPane.showMessageDialog(null, "해당 사원 없음", "동일 부서 사원",JOptionPane.INFORMATION_MESSAGE);
			return; // <<return이 왜 나오는지???
		}
		//내가 짠 코드
//		String list = null;
//		for (Employee emp : selectedList) {
//			list = emp.toString();
//		}
//		JOptionPane.showMessageDialog(null, list, "해당 부서 사원", JOptionPane.INFORMATION_MESSAGE);
		
		//선생님코드
		List<String> strList = selectedList.parallelStream().map(s -> {
			return String.format("%s(%d)", s.getEmpname(), s.getEmpno());
		}).collect(Collectors.toList());

		JOptionPane.showMessageDialog(null, strList, "동일 부서 사원", JOptionPane.INFORMATION_MESSAGE);		
	}

	@Override
	protected void actionPerformedMenuUpdate() {
		Department upDept = pList.getItem();
		pContent.setItem(upDept);
		btnAdd.setText("수정");
	}

	@Override
	protected void actionPerformedMenuDelete() {
		// 테이블에서 선택된 department 가져오기
		// delete 수행
		// pList 갱신
		Department delDept = pList.getItem();
		service.removeDepartment(delDept);
		pList.loadData();
		JOptionPane.showMessageDialog(null, delDept.getDeptName() + "부서가 삭제되었습니다.");
	}

	@Override
	protected void actionPerformedBtnUpdate(ActionEvent e) {
		// pContent에서 수정된 department 가져오기
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
		//pContent에서 작성된 department 가져오기
		//insert(add) 수행
		//pList 갱신
		//pContent clearTf() 호출하여 초기화
		Department addDept = pContent.getItem();
		service.addDepartment(addDept);
		pList.loadData();
		pContent.clearTf();
		JOptionPane.showMessageDialog(null, addDept.getDeptName() + "부서가 추가되었습니다.");
	}

}
