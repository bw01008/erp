package erp.UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;

import erp.UI.List.AbstractCustomTablePanel;
import erp.UI.content.AbstractContentPanel;
import erp.UI.exception.InvalidCheckException;
import erp.UI.exception.SqlConstraintException;

//템플릿메소드
@SuppressWarnings("serial")
public abstract class AbstractManagerUI<T> extends JFrame implements ActionListener {

	private JPanel contentPane;
	protected JButton btnAdd;	//하위에서 접근하여 버튼 이름을 변경해야하기때문에 private > protected로 바꿔줬다. 
	private JButton btnCancel;

	protected AbstractContentPanel<T> pContent; 
	protected AbstractCustomTablePanel<T> pList;
	protected JMenuItem empListByTitleItem;
	//상수로 선언
	protected static final String TITLE_MENU = "동일직책 사원보기"; 
	protected static final String DEPT_MENU = "동일부서 사원보기"; 
	protected static final String EMP_MENU = "사원 세부정보 보기"; 

	public AbstractManagerUI() {

		setService(); // 1. 서비스 연결
		initialize(); // 2. 컴포넌트 초기화
		tableLoadData(); // 3. 데이터 불러오기
	}

	protected abstract void setService(); // 추상메소드로 뺀다. 하위에서 서비스알아서 불러라
	
	private void initialize() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 461, 398);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

		pContent = createContentPanel(); // 메소드로 뺀다.
		contentPane.add(pContent);

		JPanel pBtn = new JPanel();
		contentPane.add(pBtn);

		btnAdd = new JButton("추가");
		btnAdd.addActionListener(this);
		pBtn.add(btnAdd);

		btnCancel = new JButton("취소");
		btnCancel.addActionListener(this);
		pBtn.add(btnCancel);

		pList = createTablePanel(); // 메소드로 뺀다.

		contentPane.add(pList);

		JPopupMenu popupMenu = createPopupMenu(); // 팝업메뉴 생성하는 메소드 따로 만들어서 호출)
		pList.setPopupMenu(popupMenu); // pList에 달아주지 않으면 UI에서 동작하지 않는다. 까먹을 수 있으니 꼭 기억하자
	}
	
	protected abstract void tableLoadData();

	protected abstract AbstractContentPanel<T> createContentPanel();

	protected abstract AbstractCustomTablePanel<T> createTablePanel();
	
	protected abstract void actionPerformedMenuGubun();

	protected abstract void actionPerformedMenuUpdate();

	protected abstract void actionPerformedMenuDelete();
	
	protected abstract void actionPerformedBtnUpdate(ActionEvent e);

	protected abstract void actionPerformedBtnAdd(ActionEvent e);

	// 우클릭했을 때 팝업메뉴가 뜬다.
	private JPopupMenu createPopupMenu() {
		JPopupMenu popupMenu = new JPopupMenu(); // 팝업메뉴 생성

		JMenuItem updateItem = new JMenuItem("수정"); // 팝업 메뉴 아이템 생성
		updateItem.addActionListener(this);
		popupMenu.add(updateItem);

		JMenuItem deleteItem = new JMenuItem("삭제");
		deleteItem.addActionListener(this);
		popupMenu.add(deleteItem);

		empListByTitleItem = new JMenuItem("동일직책 사원보기");
		empListByTitleItem.addActionListener(this);
		popupMenu.add(empListByTitleItem);

		return popupMenu;
	}



	public void actionPerformed(ActionEvent e) {

		try {
			if (e.getSource() instanceof JMenuItem) {
				if (e.getActionCommand().equals("삭제")) {
					actionPerformedMenuDelete(); // 삭제 시 수행하는 코드를 메소드로 뺐다.
				}
				// 테이블에서 수정하고자 하는 직책을 선택 후, 우클릭해서 수정메뉴를 선택
				if (e.getActionCommand().equals("수정")) {
					actionPerformedMenuUpdate(); // 수정시 수행하는 코드를 메소드로 뻈다.
				}
				// 동일직책 사원보기
				if (e.getActionCommand().contentEquals(AbstractManagerUI.TITLE_MENU) ||
					e.getActionCommand().contentEquals(AbstractManagerUI.DEPT_MENU) ||
					e.getActionCommand().contentEquals(AbstractManagerUI.EMP_MENU)) {
					/*
					 * 1. EmployeeDao -> selectEmployeeByTitle() 추가 2. EmployeeDaoImpl ->
					 * selectEmployeeByTitle() 구현 3. EmployeeDaoTest -> Test하기 4. TitleService ->
					 * EmployeeDaoImpl field 추가 및 메서드 추가 5. 아래 기능 추가 6. 예외찾아서 추가하기 (신규 직책 추가 시
					 * NullPointException)
					 */
					actionPerformedMenuGubun(); // 동일직책 사원보기 시 수행하는 코드를 메소드로 뺐다.
				}
			
			} else {
				if (e.getSource() == btnCancel) {
					actionPerformedBtnCancel(e);
				}

				if (e.getSource() == btnAdd) {
					if (btnAdd.getText().contentEquals("추가")) { // 버튼 이름이 '추가' 일때
						actionPerformedBtnAdd(e);
					} else { // 버튼 이름이 '추가'가 아닐때(수정일때)
						actionPerformedBtnUpdate(e);
					}

				}
			}

		} catch (InvalidCheckException | SqlConstraintException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage());
			pContent.clearTf();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	// 수정버튼을 눌렀을 때 실행되는 메소드


	protected void actionPerformedBtnCancel(ActionEvent e) {
		pContent.clearTf();

		if (btnAdd.getText().contentEquals("수정")) {
			btnAdd.setText("추가");
		}
	}
}
