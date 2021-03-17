package erp.UI.content;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import erp.dto.Title;
import erp.UI.exception.InvalidCheckException;

@SuppressWarnings("serial")
public class TitlePanel extends AbstractContentPanel<Title> {
	
	protected JTextField tfTno;
	protected JTextField tfTname;

	public TitlePanel() {

		initialize();
	}

	public void initialize() {
		setBorder(new TitledBorder(null, "직책정보", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new GridLayout(0, 2, 20, 20));

		JLabel lblTno = new JLabel("직책번호");
		lblTno.setHorizontalAlignment(SwingConstants.TRAILING);
		add(lblTno);

		tfTno = new JTextField();
		tfTno.setColumns(10);
		add(tfTno);

		JLabel lblTname = new JLabel("직책이름");
		lblTname.setHorizontalAlignment(SwingConstants.TRAILING);
		add(lblTname);

		tfTname = new JTextField();
		tfTname.setColumns(10);
		add(tfTname);
	}

	@Override
	public void setItem(Title item) {
		//매개변수로 받아온 title 객체의 요소들을 String형태로 변환 후 수정할 수 있도록 텍스트 필드에 세팅
		tfTno.setText(String.valueOf(item.getTno()));
		tfTname.setText(item.getTname());
		//Tno는 수정할 수 없도록 비활성화 설정
		tfTno.setEditable(false);
	}

	@Override
	public Title getItem() {
		//유효성 체크
		validCheck();
		//텍스트필드에 작성된 것들을 Title클래스 필드에 선언된 타입에 맞춰 형변환 후 가져오기
		int tno = Integer.parseInt(tfTno.getText().trim());
		String tname = tfTname.getText().trim();
		return new Title(tno, tname);
	}

	@Override
	public void validCheck() {
		//유효성 체크 : 해당 텍스트 필드 중 하나라도 공백일 경우, 입력을 유도하는 예외처리
		if (tfTno.getText().contentEquals("") || tfTname.getText().equals("")) {
			throw new InvalidCheckException();
		}

	}

	@Override
	public void clearTf() {
		//TitlePanel 초기화
		tfTno.setText("");
		tfTname.setText("");
		//Tno텍스트 필드 비활성화 풀기
		if (!tfTno.isEditable()) {
			tfTno.setEditable(true);
		}
	}
}
