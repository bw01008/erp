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

	private TitleService service; // 받아오는 service클래스가 다르기때문에 하위에 선언해준다.

	@Override
	protected void setService() {
		service = new TitleService();
	}
	
	public TitleManagerUI() {
		empListByTitleItem.setText(AbstractManagerUI.TITLE_MENU);		//상위클래스에 선언된 상수 호출
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
		Title t = pList.getItem(); // 1. 테이블에서 선택된 Title 객체를 가져오기
		List<Employee> selectedList = service.showEmployee(t); // 2. 선택된 title을 가지는 employee를 List로 반환(service연결)
		// 선택한 직책과 동일한 사원이 없을 시,
		if (selectedList == null) {
			JOptionPane.showMessageDialog(null, "해당하는 사원이 없습니다.", "동일 직책 사원", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		// 동일 직책 사원 출력해주는 팝업창 띄우기
		String list = null;
		for (Employee emp : selectedList) {
			list = emp.toString();
		}
		JOptionPane.showMessageDialog(null, list, "동일 직책 사원", JOptionPane.INFORMATION_MESSAGE);
	}

	@Override
	// 수정 : 팝업메뉴에서 수정버튼("이걸 수정할래~")
	protected void actionPerformedMenuUpdate() {
		// 1. 테이블에서 선택된 직책 객체로 받아오기
		// 2. 선택된 객체를 ContentPanel에 수정할 수 있도록 가져오기
		// 3. 추가버튼을 수정버튼으로 바꾼다.
		Title upTitle = pList.getItem();
		pContent.setItem(upTitle);
		btnAdd.setText("수정");
	}

	@Override
	// 삭제
	protected void actionPerformedMenuDelete() {
		// 1. 테이블에서 선택된 직책을 객체로 받아온다.
		// 2. 서비스와 연동해서 선택된 객체를 삭제하는 메소드를 호출
		// *** 3. 선택된 직책이 삭제된 후 데이터를 새로 읽어와서 테이블에 반영한다.
		Title delTitle = pList.getItem();
		service.removeTitle(delTitle);
		pList.loadData();
		JOptionPane.showMessageDialog(null, delTitle + " 삭제되었습니다.");
	}

	@Override
	// 수정 : JFrame에 만들어 둔 수정버튼(contentPane에 작성한걸 반영해줘~)
	protected void actionPerformedBtnUpdate(ActionEvent e) {
		// pContent에서 수정된 title 가져오기
		// update 수행
		// pList 갱신***(까먹기 쉽다)
		// pContent clearTf()호출하여 초기화
		// btnAdd 텍스트 변경 수정->추가

		Title updateTitle = pContent.getItem();
		service.modifyTitle(updateTitle);
		pList.loadData();
		pContent.clearTf();
		btnAdd.setText("추가");
		JOptionPane.showMessageDialog(null, updateTitle.getTname() + "정보가 수정 완료되었습니다.");

	}

	@Override
	// 추가 : 버튼 눌렀을 때
	protected void actionPerformedBtnAdd(ActionEvent e) {
		// pContent에서 추가된 title 가져오기
		// insert(add)수행
		// pList갱신
		// pContent clearTf()호출하여 초기화
		Title title = pContent.getItem();
		service.addTitle(title);
		pList.loadData();
		pContent.clearTf();
		JOptionPane.showMessageDialog(null, title + "추가했습니다.");
	}

}
