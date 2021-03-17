package erp.UI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import erp.Service.EmployeeDetailService;
import erp.UI.content.EmployeeDetailPanel;
import erp.dto.Employee;
import erp.dto.EmployeeDetail;

@SuppressWarnings("serial")
public class EmployeeDetailUI extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JPanel pBtns;
	private EmployeeDetailPanel pItem;
	private JButton btnAdd;
	private JButton btnCancel;
	private EmployeeDetailService service;
//	private JButton btnDelete;
//	private JButton btnUpdate;

	public EmployeeDetailUI(boolean isBtns, EmployeeDetailService service) {

		this.service = service;

		initialize(isBtns);
	}
	
	//초기화 메소드에 매개값으로 inBtns를 넣었다.
	//매개변수 isBtns가 true이면 추가/취소 버튼을 구현해둔 패널이 보이고, false인 경우에는 보이지 않게 설정했다.
	private void initialize(boolean isBtns) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 541);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		pItem = new EmployeeDetailPanel();	//몹핑
		contentPane.add(pItem, BorderLayout.CENTER);

		pBtns = new JPanel();
		contentPane.add(pBtns, BorderLayout.SOUTH);

		btnAdd = new JButton();
		btnAdd.addActionListener(this);
		pBtns.add(btnAdd);

		btnCancel = new JButton();
		btnCancel.addActionListener(this);
		pBtns.add(btnCancel);
		
		// 매개변수 isBtns가 true이면 추가/취소 버튼을 구현해둔 패널이 보이고, false인 경우에는 보이지 않게 설정했다.
		if (isBtns) {
			btnAdd.setText("추가");
			btnCancel.setText("삭제");
		} else { // 사원 상제정보보기를 눌렀을때 데이터가 없으면 추가/취소 버튼을 구현한 패널을 보여준다
			btnAdd.setText("수정");
			btnCancel.setText("삭제");

//			// 사원상세정보보기 눌렀을 때 데이터가 있으면 삭제버튼만 보여준다. (단! 버튼은 패널 위에 올라가있어야하기때문에 한번 더 패널을 생성해줘야한다)
//			pBtns = new JPanel();
//			contentPane.add(pBtns, BorderLayout.SOUTH);
//			
//			btnUpdate = new JButton("수정");
//			btnUpdate.addActionListener(this);
//			pBtns.add(btnUpdate);
//			
//			btnDelete = new JButton("삭제");
//			btnDelete.addActionListener(this);
//			pBtns.add(btnDelete);
		}
	}

	public void setEmpNo(Employee empNo) {
		pItem.setTfEmpno(empNo);
	}

	public void setDetailItem(EmployeeDetail empDetail) {
//		btnAdd.setText("수정");
		pItem.setItem(empDetail);
	}

	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getActionCommand().contentEquals("삭제")) {
			actionPerformedBtnDelete(arg0);
		}
		if (arg0.getActionCommand().contentEquals("취소")) {
			actionPerformedBtnCancel(arg0);
		}
		if (arg0.getActionCommand().contentEquals("추가")) {
			actionPerformedBtnAdd(arg0);
		}

		if (arg0.getActionCommand().contentEquals("수정")) {
			actionPerformedBtnUpdate(arg0);
		}

	}

	private void actionPerformedBtnUpdate(ActionEvent arg0) {
		// 1. 수정해서 입력된 upEmpDetail 가져오기
		// 2. service에 연결 > update실행
		// 3. pItem clearTf() 호출해서 초기화
		// 4. 완료 후 자동 창닫기 
		EmployeeDetail upEmpDetail = pItem.getItem();
		service.modifyEmployeeDetail(upEmpDetail);
		pItem.clearTf();
		JOptionPane.showMessageDialog(null, "수정완료");
		dispose(); // 자동으로 해당 창 닫기
	}

	// 사원상세정보 추가
	protected void actionPerformedBtnAdd(ActionEvent arg0) {
		// 1. 입력된 EmployeeDetail 가져오기
		// 2. service에 적용 > insert(add)실행
		// 3. pItem clearTf() 호출해서 초기화
		// 4. 완료 후 자동 창닫기 
		EmployeeDetail newEmpDetail = pItem.getItem();
		service.addEmployeeDetail(newEmpDetail);
		pItem.clearTf();
		JOptionPane.showMessageDialog(null, "추가완료");
		dispose();
	}

	protected void actionPerformedBtnCancel(ActionEvent arg0) {
		pItem.clearTf();
		if (btnAdd.getText().contentEquals("수정")) {
			btnAdd.setText("추가");
		}
	}

	protected void actionPerformedBtnDelete(ActionEvent arg0) {
		EmployeeDetail empDetail = pItem.getItem();
		service.removeEmployeeDetail(new Employee(empDetail.getEmpNo()));
		pItem.clearTf();
		JOptionPane.showInternalMessageDialog(null, "삭제완료");
	}
}
