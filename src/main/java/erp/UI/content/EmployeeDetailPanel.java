package erp.UI.content;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.toedter.calendar.JDateChooser;

import erp.UI.exception.InvalidCheckException;
import erp.dto.EmployeeDetail;

@SuppressWarnings("serial")
public class EmployeeDetailPanel extends AbstractContentPanel<EmployeeDetail> implements ActionListener {

	private JPasswordField pfPass1;
	private JPasswordField pfPass2;
	private String imgPath = System.getProperty("user.dir") + File.separator + "image" + File.separator; // 이미지경로지정
	private JFileChooser chooserPath = new JFileChooser(System.getProperty("user.dir"));
	private JLabel lblPic;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JButton btnAddPic;
	private JLabel lblConfirm;
	private JDateChooser dateHire;

	public EmployeeDetailPanel() {
		initialize();
		loadPic(null);
	}

	private void loadPic(String imgFilePath) {
		Image changeImage = null;
		if (imgPath == null) {
			ImageIcon icon = new ImageIcon(imgPath + "noimage.jpg"); // 이미지 받아와서 아이콘 생성
			changeImage = icon.getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH); // 이미지 사이즈 조정
		} else {
			ImageIcon icon = new ImageIcon(imgFilePath); // 이미지 받아와서 아이콘 생성
			changeImage = icon.getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH); // 이미지 사이즈 조정
		}
		ImageIcon changeIcon = new ImageIcon(changeImage); // 사이즈조정한 이미지로 아이콘 재생성
		lblPic.setIcon(changeIcon); // 레이블에 아이콘 달기
	}

	private void initialize() {
		setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "사원 세부 정보", TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(0, 0, 0)));
		setLayout(new BorderLayout(0, 0));

		JPanel pTop = new JPanel();
		add(pTop, BorderLayout.NORTH);

		JPanel pPic = new JPanel();
		pTop.add(pPic);
		pPic.setLayout(new BorderLayout(0, 0));

		lblPic = new JLabel();
		lblPic.setPreferredSize(new Dimension(120, 150));
		pPic.add(lblPic, BorderLayout.CENTER);

		btnAddPic = new JButton("사진 추가");
		btnAddPic.addActionListener(this);
		pPic.add(btnAddPic, BorderLayout.SOUTH);

		JPanel pItem = new JPanel();
		add(pItem, BorderLayout.CENTER);
		pItem.setLayout(new GridLayout(0, 1, 0, 0));

		JPanel pContent = new JPanel();
		pItem.add(pContent);
		pContent.setLayout(new GridLayout(0, 2, 10, 0));

		JLabel lblHireDate = new JLabel("입사일");
		lblHireDate.setHorizontalAlignment(SwingConstants.RIGHT);
		pContent.add(lblHireDate);

		dateHire = new JDateChooser(new Date());
		dateHire.setDateFormatString("yyyy.MM.dd");
		pContent.add(dateHire);

		JLabel lblGender = new JLabel("성별");
		lblGender.setHorizontalAlignment(SwingConstants.RIGHT);
		pContent.add(lblGender);

		JPanel pGender = new JPanel();
		pContent.add(pGender);

		rdbtnFemale = new JRadioButton("여자");
		rdbtnFemale.setSelected(true); // 디폴트는 여자로 선택되어있도록
		buttonGroup.add(rdbtnFemale); // 버튼 그룹 묶어야 배타성이 부여된다.(디자인에서 라디오 버튼 모두 선택한 상태로 우클릭 > 버튼그룹 > 스탠다드)
		pGender.add(rdbtnFemale);

		JRadioButton rdbtnMale = new JRadioButton("남자");
		buttonGroup.add(rdbtnMale); // 버튼 그룹 묶어야 배타성이 부여된다.(디자인에서 라디오 버튼 모두 선택한 상태로 우클릭 > 버튼그룹 > 스탠다드)
		pGender.add(rdbtnMale);

		JLabel lblPass1 = new JLabel("비밀번호");
		lblPass1.setHorizontalAlignment(SwingConstants.RIGHT);
		pContent.add(lblPass1);

		pfPass1 = new JPasswordField();
		pfPass1.getDocument().addDocumentListener(listener);
		pContent.add(pfPass1);

		JLabel lblPass2 = new JLabel("비밀번호 확인");
		lblPass2.setHorizontalAlignment(SwingConstants.RIGHT);
		pContent.add(lblPass2);

		pfPass2 = new JPasswordField();
		pfPass2.getDocument().addDocumentListener(listener);
		pContent.add(pfPass2);

		JPanel pSpace = new JPanel();
		pContent.add(pSpace);

		lblConfirm = new JLabel("New label");
		lblConfirm.setFont(new Font("굴림", Font.BOLD, 20));
		lblConfirm.setForeground(Color.RED);
		lblConfirm.setHorizontalAlignment(SwingConstants.CENTER);
		pContent.add(lblConfirm);
	}

	@Override
	public void setItem(EmployeeDetail item) {
		// TODO Auto-generated method stub

	}

	@Override
	public EmployeeDetail getItem() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void validCheck() {
		if (lblConfirm.getText().equals("일치")) {
			throw new InvalidCheckException("비밀번호 불일치");
		}
	}

	@Override
	public void clearTf() {
		loadPic(null);
		dateHire.setDate(new Date());
		rdbtnFemale.setSelected(true);
		pfPass1.setText("");
		pfPass2.setText("");
		lblConfirm.setText("");

	}

	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == btnAddPic) {
			actionPerformedBtnAddPic(arg0);
		}
	}

	protected void actionPerformedBtnAddPic(ActionEvent arg0) {
		// 파일 필터 객체 생성 > 보이는 파일의 유형을 결정한다.
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & PNG & GIF images", "jpg", "png", "gif"); // 설명과
																														// 확장자
		chooserPath.setFileFilter(filter);
		chooserPath.setFileSelectionMode(JFileChooser.FILES_ONLY);

		// 열기 다이얼로그 출력
		int res = chooserPath.showOpenDialog(null);
		if (res != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(null, "파일을 선택하지 않았습니다.", "경고", JOptionPane.WARNING_MESSAGE);
			return;
		}
		String chooseFilePath = chooserPath.getSelectedFile().getPath();
		loadPic(chooseFilePath);
	}

	DocumentListener listener = new DocumentListener() {

		@Override
		public void removeUpdate(DocumentEvent e) {
			getMessage();

		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			getMessage();

		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			getMessage();

		}

		private void getMessage() {
			String pass1 = new String(pfPass1.getPassword());
			String pass2 = new String(pfPass2.getPassword());

			if (pass1.equals(pass2)) {
				lblConfirm.setText("일치");
			} else {
				lblConfirm.setText("불일치");
			}
		}
	};
	private JRadioButton rdbtnFemale;
}
