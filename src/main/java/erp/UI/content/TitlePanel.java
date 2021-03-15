package erp.UI.content;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import erp.dto.Title;
import erp.UI.exception.InvalidCheckException;

@SuppressWarnings("serial")
public class TitlePanel extends InterfaceItem<Title> {
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
	public void clearTf() {
		tfTno.setText("");
		tfTname.setText("");
		
		if(!tfTno.isEditable()) {
			tfTno.setEditable(true);
		}
	}

	@Override
	public void setItem(Title item) {
		tfTno.setText(String.valueOf(item.getTno()));
		tfTname.setText(item.getTname());
		
		tfTno.setEditable(false);
	}

	@Override
	public Title getItem() {
		validCheck();// 아래 두개의 값이 입력되어야게끔 확인
		int tno = Integer.parseInt(tfTno.getText().trim());
		String tname = tfTname.getText().trim();
		return new Title(tno, tname);
	}

	@Override
	public void validCheck() {
		if (tfTno.getText().contentEquals("") || tfTname.getText().equals("")) {
			throw new InvalidCheckException();
		}

	}



}
