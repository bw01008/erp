package erp.UI.content;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import erp.UI.exception.InvalidCheckException;
import erp.dto.Department;

@SuppressWarnings("serial")
public class DepartmentPanel extends AbstractContentPanel<Department> {
	private JTextField tfDeptNo;
	private JTextField tfDeptName;
	private JTextField tfFloor;

	public DepartmentPanel() {
		initialize();
	}

	private void initialize() {
		// TitleBorder설정
		setBorder(new TitledBorder(null, "부서정보", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		// 레이아웃 설정
		setLayout(new GridLayout(0, 2, 10, 10));

		// 레이블 생성
		JLabel lblDeptNo = new JLabel("부서번호");
		lblDeptNo.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblDeptNo);

		// 텍스트필드 생성
		tfDeptNo = new JTextField();
		tfDeptNo.setColumns(10);
		add(tfDeptNo);

		// 레이블 생성
		JLabel lblDeptName = new JLabel("부서명");
		lblDeptName.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblDeptName);

		// 텍스트필드 생성
		tfDeptName = new JTextField();
		tfDeptName.setColumns(10);
		add(tfDeptName);

		// 레이블 생성
		JLabel lblFloor = new JLabel("위치");
		lblFloor.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblFloor);

		// 텍스트필드 생성
		tfFloor = new JTextField();
		tfFloor.setColumns(10);
		add(tfFloor);
	}

//	//텍스트 필드에 부서 정보 설정해주는 메소드 > 매개변수로 받은 객체의 필드값으로 설정해준다
//	public void setDepartment(Department department) {
////		tfDeptNo.setText(String.valueOf(department.getDeptNo()));
//		tfDeptNo.setText(department.getDeptNo() + "");
//		tfDeptName.setText(department.getDeptName());
//		tfFloor.setText(department.getFloor() + "");
//	}
//
//	//인스턴스 필드값으로 만든 객체를 반환해주는 메소드
//	public Department getDepartment() {
//		int deptNo = Integer.parseInt(tfDeptNo.getText().trim());
//		String deptName = tfDeptName.getText().trim();
//		int floor = Integer.parseInt(tfFloor.getText().trim());
//		return new Department(deptNo, deptName, floor);
//	}
//	
////	private void validCheck() {
////		if(tfDeptNo.)
////	}

	@Override
	public void setItem(Department item) {
		//매개변수로 받아온 department의 요소들을 String형태로 변환 후 수정할 수 있도록 텍스트 필드에 세팅
		tfDeptNo.setText(String.valueOf(item.getDeptNo()));
		tfDeptName.setText(item.getDeptName());
		tfFloor.setText(item.getFloor() + "");
		//deptNo는 수정할 수 없도록 설정해줘야한다.
		tfDeptNo.setEditable(false);
	}
	
	@Override
	public Department getItem() {
		//유효성 체크
		validCheck();
		//텍스트 필드에 작성된 것들을 Department클래스 필드에 선언된 타입에 맞춰 형변환해서 가져오기
		int deptNo = Integer.parseInt(tfDeptNo.getText().trim());
		String deptName = tfDeptName.getText().trim();
		int floor = Integer.parseInt(tfFloor.getText().trim());
		return new Department(deptNo, deptName, floor);
	}
	
	@Override
	public void validCheck() {
		//유효성 체크 : 해당 텍스트 필드 중 하나라도 공백일 경우, 입력을 유도하는 예외처리
		if (tfDeptNo.getText().contentEquals("") 
				|| tfDeptName.getText().equals("") 
				|| tfFloor.getText().equals("")) {
			throw new InvalidCheckException();
		}
		
	}
	
	@Override
	public void clearTf() {
		// DepartmentPanel 초기화
		tfDeptNo.setText("");
		tfDeptName.setText("");
		tfFloor.setText("");
		
		//deptNo텍스트 필드 비활성화 풀기
		if(!tfDeptNo.isEditable()) {
			tfDeptNo.setEditable(true);
		}
	}

}
