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

import erp.Service.DepartmentService;
import erp.UI.List.DepartmentTablePanel;
import erp.UI.content.DeptPanel;
import erp.UI.content.InterfaceItem;
import erp.UI.exception.InvalidCheckException;
import erp.UI.exception.NotSelectedException;
import erp.UI.exception.SqlConstraintException;
import erp.dto.Department;
import erp.dto.Employee;

@SuppressWarnings("serial")
public class DepartmentManager extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JButton btnAdd;
	private JButton btnCancel;
	private InterfaceItem<Department> pContent;
	private JPanel pBtn;
	private DepartmentTablePanel pList;
	private DepartmentService service;

	public DepartmentManager() {
		service = new DepartmentService(); // service객체를 생성해준다.
		initialize();
	}

	private void initialize() {
		setTitle("부서관리");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 513);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

		pContent = new DeptPanel();
		contentPane.add((DeptPanel) pContent);

		pBtn = new JPanel();
		contentPane.add(pBtn);

		btnAdd = new JButton("추가");
		btnAdd.addActionListener(this);
		pBtn.add(btnAdd);

		btnCancel = new JButton("취소");
		btnCancel.addActionListener(this);
		pBtn.add(btnCancel);

		pList = new DepartmentTablePanel();
		pList.setService(service); // 패널도 프레임에 생성된 service 객체를 쓸 수 있게 해준다.
		pList.loadData();
		contentPane.add(pList);

		JPopupMenu popupMenu = createPopupMenu();
		//
		pList.setPopupMenu(popupMenu); // *** 까먹고 해주지 않으면 UI에서 작동하지 않는다.

	}

	private JPopupMenu createPopupMenu() {
		//
		JPopupMenu popupMenu = new JPopupMenu();

		// 수정, 삭제, 해당부서직원 검색 아이템 만들기
		JMenuItem updateItem = new JMenuItem("수정");
		updateItem.addActionListener(popupMenuListener);
		popupMenu.add(updateItem);

		JMenuItem deleteItem = new JMenuItem("삭제");
		deleteItem.addActionListener(popupMenuListener);
		popupMenu.add(deleteItem);

		JMenuItem empListByDeptItem = new JMenuItem("해당부서 사원보기");
		empListByDeptItem.addActionListener(popupMenuListener);
		popupMenu.add(empListByDeptItem);
		return popupMenu;
	}

	ActionListener popupMenuListener = new ActionListener() {
		// 수정, 삭제, 해당부서 사원보기를 눌렀을 때 동작하는 각 코드를 작성하세요.
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				if (e.getActionCommand().equals("삭제")) {
					Department delDept = pList.getItem();// 선택된 항목을 객체로 가져온다.
					service.removeDepartment(delDept);// 삭제쿼리문을 동작하는 메소드를 호출해서 데이터를 삭제한다.
					pList.loadData();
					JOptionPane.showMessageDialog(null, delDept.getDeptName() + "부서가 삭제되었습니다.");
				} // end of if

				if (e.getActionCommand().equals("수정")) {
					Department upDept = pList.getItem();
					pContent.setItem(upDept);
					btnAdd.setText("수정");
				}
				if (e.getActionCommand().equals("해당부서 사원보기")) {
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
			} catch (NotSelectedException | SqlConstraintException e2) {
				JOptionPane.showMessageDialog(null, e2.getMessage());
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	};

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnCancel) {
			actionPerformedBtnCancel(e);
		}
		try {
			if (e.getSource() == btnAdd) {
				if (btnAdd.getText().contentEquals("추가")) {
					actionPerformedBtnAdd(e);
				} else {
					actionPerformedBtnUpdate(e);
				}
			}
		} catch (InvalidCheckException | SqlConstraintException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void actionPerformedBtnUpdate(ActionEvent e) {
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

	protected void actionPerformedBtnAdd(ActionEvent e) {
		Department addDept = pContent.getItem(); // 텍스트필드에 있는 정보로 객체 생성해서 가져온다.
		service.addDepartment(addDept);
		pList.loadData(); // 추가 후 loadData()메소드를 꼭 호출해줘야 반영된다.
		pContent.clearTf();
		JOptionPane.showMessageDialog(null, addDept.getDeptName() + "부서가 추가되었습니다.");
	}

	protected void actionPerformedBtnCancel(ActionEvent e) {
		pContent.clearTf();
		
		if(btnAdd.getText().contentEquals("수정")) {
			btnAdd.setText("추가");
		}
	}
}
