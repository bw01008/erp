package erp.UI;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JOptionPane;

import erp.Service.TitleService;
import erp.UI.List.AbstractCustomTablePanel;
import erp.UI.List.TitleTablePanel;
import erp.UI.content.AbstractContentPanel;
import erp.UI.content.TitlePanel;
import erp.dto.Employee;
import erp.dto.Title;

@SuppressWarnings("serial")
public class TitleManagerUI extends AbstractManagerUI<Title> {

	private TitleService service;	//받아오는 service클래스가 다르기때문에 하위에 선언해준다.
	
	
	public TitleManagerUI() {
		empListByTitleItem.setText(AbstractManagerUI.TITLE_MENU);
	}

	@Override
	protected void setService() {
		service = new TitleService();
	}

	@Override
	protected void tableLoadData() {
		// 패널도 프레임에 생성된 service 객체를 쓸 수 있게 해준다.
		((TitleTablePanel) pList).setService(service);
		// 데이터를 읽어와서 리스트 > 배열 > 모델로 만들어서 테이블에 달아주는 메소드를 pList에 호출하게 되면 UI에 데이터가 반영된다.
		pList.loadData();
	}

	@Override
	protected AbstractContentPanel<Title> createContentPanel() {
		return new TitlePanel();
	}

	@Override
	protected AbstractCustomTablePanel<Title> createTablePanel() {
		return new TitleTablePanel();
	}

	@Override
	protected void actionPerformedMenuGubun() {
		/*
		 * 1. EmployeeDao -> selectEmployeeByTitle() 추가 2. EmployeeDaoImpl ->
		 * selectEmployeeByTitle() 구현 3. EmployeeDaoTest -> Test하기 4. TitleService ->
		 * EmployeeDaoImpl field 추가 및 메서드 추가 5. 아래 기능 추가 6. 예외찾아서 추가하기 (신규 직책 추가 시
		 * NullPointException)
		 */
		Title t = pList.getItem(); // 선택된 Title 객체를 받는다.
		List<Employee> selectedList = service.showEmployee(t); // 선택된 title을 가지는 employee를 List로 반환
		if (selectedList == null) {
			JOptionPane.showMessageDialog(null, "해당하는 사원이 없습니다.", "동일 직책 사원", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		String list = null;
		for (Employee emp : selectedList) {
			list = emp.toString();
		}
		JOptionPane.showMessageDialog(null, list, "동일 직책 사원", JOptionPane.INFORMATION_MESSAGE);
	}

	@Override
	protected void actionPerformedMenuUpdate() {
		Title upTitle = pList.getItem(); // 선택된 항목 객체로 받아오기
		pContent.setItem(upTitle); // 텍스트필드에 선택된 객체 가져오기
		btnAdd.setText("수정"); // 추가버튼을 수정버튼으로 바꾼다.
	}

	@Override
	protected void actionPerformedMenuDelete() {
		Title delTitle = pList.getItem(); // 선택한 직책을 객체로 받아온다.
		service.removeTitle(delTitle); // 삭제하는 쿼리문을 실행하는 메소드를 불러온다(service)
		pList.loadData(); // 선택된 직책이 삭제된 후 데이터를 새로 읽어와서 반영한다.
		JOptionPane.showMessageDialog(null, delTitle + " 삭제되었습니다.");
	}

	@Override
	protected void actionPerformedBtnUpdate(ActionEvent e) {
		// pContent에서 수정된 title 가져오기
		// update 수행
		// pList 갱신
		// pContent clearTf()호출하여 초기화
		// btnAdd 텍스트 변경 수정->추가

		Title updateTitle = pContent.getItem(); // 텍스트필드에 작성된 텍스트를 이용해 객체를 생성해서 반환받는다.
		service.modifyTitle(updateTitle); // 수정하는 쿼리문을 실행하는 메소드를 호출한다.
		pList.loadData(); // 쿼리문이 적용된 후에 데이터를 다시 읽어와서 반영한다.
		pContent.clearTf();
		btnAdd.setText("추가");
		JOptionPane.showMessageDialog(null, updateTitle.getTname() + "정보가 수정 완료되었습니다.");

	}

	@Override
	protected void actionPerformedBtnAdd(ActionEvent e) {
		Title title = pContent.getItem(); // 텍스트필드에 작성된 텍스트를 가져와서 객체 생성해서 반환받는다.
		service.addTitle(title); // 데이터 추가하는 쿼리문을 실행하는 메소드를 호출
		pList.loadData(); // 쿼리문 적용된 후에 데이터를 다시 읽어와서 반영한다.
		pContent.clearTf();
		JOptionPane.showMessageDialog(null, title + "추가했습니다.");
	}

}
