package erp.UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;

import erp.Service.TitleService;
import erp.UI.List.TitleTablePanel;
import erp.UI.content.TitlePanel;
import erp.UI.exception.InvalidCheckException;
import erp.UI.exception.NotSelectedException;
import erp.UI.exception.SqlConstraintException;
import erp.dto.Employee;
import erp.dto.Title;

@SuppressWarnings("serial")
public class TitleManager extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JButton btnAdd;
	private TitlePanel pContent;
	private TitleTablePanel pList;
	private TitleService service;

	public TitleManager() {
		service = new TitleService();
		initialize();
	}

	private void initialize() {
		setTitle("직책 관리");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 461, 398);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

		pContent = new TitlePanel();
		contentPane.add((TitlePanel)pContent);

		JPanel pBtn = new JPanel();
		contentPane.add(pBtn);

		btnAdd = new JButton("추가");
		btnAdd.addActionListener(this);
		pBtn.add(btnAdd);

		btnCancel = new JButton("취소");
		btnCancel.addActionListener(this);
		pBtn.add(btnCancel);

		pList = new TitleTablePanel();
		//패널도 프레임에 생성된 service 객체를 쓸 수 있게 해준다. 
		pList.setService(service);
		 // 데이터를 읽어와서 리스트 > 배열 > 모델로 만들어서 테이블에 달아주는 메소드를 pList에 호출하게 되면 UI에 데이터가 반영된다.
		pList.loadData();
		contentPane.add(pList);

		JPopupMenu popupMenu = createPopupMenu(); // 팝업메뉴 생성하는 메소드 따로 만들어서 호출)
		pList.setPopupMenu(popupMenu);	//pList에 달아주지 않으면 UI에서 동작하지 않는다. 까먹을 수 있으니 꼭 기억하자
	}

	// 우클릭했을 때 팝업메뉴가 뜬다.
	private JPopupMenu createPopupMenu() {
		JPopupMenu popupMenu = new JPopupMenu(); // 팝업메뉴 생성

		JMenuItem updateItem = new JMenuItem("수정"); // 팝업 메뉴 아이템 생성
		updateItem.addActionListener(popupMenuListener);
		popupMenu.add(updateItem);

		JMenuItem deleteItem = new JMenuItem("삭제");
		deleteItem.addActionListener(popupMenuListener);
		popupMenu.add(deleteItem);

		JMenuItem empListByTitleItem = new JMenuItem("동일직책 사원보기");
		empListByTitleItem.addActionListener(popupMenuListener);
		popupMenu.add(empListByTitleItem);

		return popupMenu;
	}

	ActionListener popupMenuListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {

			try {
				// 삭제 : 테이블에 삭제하고자 하는 직책을 선택 후, 우클릭해서 삭제메뉴를 선택
				if (e.getActionCommand().equals("삭제")) {
					Title delTitle = pList.getItem(); // 선택한 직책을 객체로 받아온다.
					service.removeTitle(delTitle); // 삭제하는 쿼리문을 실행하는 메소드를 불러온다(service)
					pList.loadData(); // 선택된 직책이 삭제된 후 데이터를 새로 읽어와서 반영한다.
					JOptionPane.showMessageDialog(null, delTitle + " 삭제되었습니다.");
				}
				// 테이블에서 수정하고자 하는 직책을 선택 후, 우클릭해서 수정메뉴를 선택
				if (e.getActionCommand().equals("수정")) {
					Title upTitle = pList.getItem(); // 선택된 항목 객체로 받아오기
					pContent.setItem(upTitle); // 텍스트필드에 선택된 객체 가져오기
					btnAdd.setText("수정"); // 추가버튼을 수정버튼으로 바꾼다.
				}
				// 동일직책 사원보기
				if (e.getActionCommand().equals("동일직책 사원보기")) {
					/*
					 * 1. EmployeeDao -> selectEmployeeByTitle() 추가
					 * 2. EmployeeDaoImpl -> selectEmployeeByTitle() 구현
					 * 3. EmployeeDaoTest -> Test하기
					 * 4. TitleService -> EmployeeDaoImpl field 추가 및 메서드 추가
					 * 5. 아래 기능 추가
					 * 6. 예외찾아서 추가하기 (신규 직책 추가 시 NullPointException)
					 */
					Title t = pList.getItem(); // 선택된 Title 객체를 받는다.
					List<Employee> selectedList = service.showEmployee(t); // 선택된 title을 가지는 employee를 List로 반환
					if (selectedList == null) {
						JOptionPane.showMessageDialog(null, "해당하는 사원이 없습니다.", "동일 직책 사원",JOptionPane.INFORMATION_MESSAGE);
						return;
					}
		
					String list = null;
					for (Employee emp : selectedList) {
						list = emp.toString();
					}
					JOptionPane.showMessageDialog(null, list, "동일 직책 사원", JOptionPane.INFORMATION_MESSAGE);
				}
				
				// 직책이 선택되지 않거나 제약조건이 걸려있을 경우 예외처리로 처리해준다.
			} catch (NotSelectedException | SqlConstraintException e2) {
				JOptionPane.showMessageDialog(null, e2.getMessage());
			} catch (Exception e2) {
				e2.printStackTrace();
			}

		}

	};
	private JButton btnCancel;

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnCancel) {
			actionPerformedBtnCancel(e);
		}
		
		try {
			if (e.getSource() == btnAdd) {
				if (btnAdd.getText().contentEquals("추가")) { // 버튼 이름이 '추가' 일때
					actionPerformedBtnAdd(e);
				} else { // 버튼 이름이 '추가'가 아닐때(수정일때)
					actionPerformedBtnUpdate(e);
				}

			}
			
		} catch (InvalidCheckException | SqlConstraintException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage());
			pContent.clearTf();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	//수정버튼을 눌렀을 때 실행되는 메소드
	private void actionPerformedBtnUpdate(ActionEvent e) {
		//pContent에서 수정된 title 가져오기
		//update 수행
		//pList 갱신
		//pContent clearTf()호출하여 초기화
		//btnAdd 텍스트 변경 수정->추가
		
		Title updateTitle = pContent.getItem();	//텍스트필드에 작성된 텍스트를 이용해 객체를 생성해서 반환받는다.
		service.modifyTitle(updateTitle);	// 수정하는 쿼리문을 실행하는 메소드를 호출한다.
		pList.loadData();	//쿼리문이 적용된 후에 데이터를 다시 읽어와서 반영한다.
		pContent.clearTf();
		btnAdd.setText("추가");
		JOptionPane.showMessageDialog(null, updateTitle.getTname() + "정보가 수정 완료되었습니다.");

	}

	protected void actionPerformedBtnAdd(ActionEvent e) {
		Title title = pContent.getItem();	//텍스트필드에 작성된 텍스트를 가져와서 객체 생성해서 반환받는다.
		service.addTitle(title);	//데이터 추가하는 쿼리문을 실행하는 메소드를 호출
		pList.loadData();	//쿼리문 적용된 후에 데이터를 다시 읽어와서 반영한다.
		pContent.clearTf();
		JOptionPane.showMessageDialog(null, title + "추가했습니다.");	
	}
	protected void actionPerformedBtnCancel(ActionEvent e) {
		pContent.clearTf();
		
		if(btnAdd.getText().contentEquals("수정")) {
			btnAdd.setText("추가");
		}
	}
}
