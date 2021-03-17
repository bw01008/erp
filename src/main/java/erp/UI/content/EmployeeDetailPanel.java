package erp.UI.content;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.toedter.calendar.JDateChooser;

import erp.UI.exception.InvalidCheckException;
import erp.dto.Employee;
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
	private JDateChooser dateHire;
	private JRadioButton rdbtnFemale;
	private JLabel lblConfirm;
	private JTextField tfEmpno;
	private JRadioButton rdbtnMale;

	
	public EmployeeDetailPanel() {
		initialize();
		loadPic(null);
	}
	
	
	private void loadPic(String imgFilePath) {
		Image changeImage = null;
			//이미지 파일없을 경우,
		if (imgFilePath == null) {
			ImageIcon icon = new ImageIcon(imgPath + "noimage.jpg"); // 이미지 받아와서 아이콘 생성
			changeImage = icon.getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH); // 이미지 사이즈 조정
		} else {
			//이미지 파일 있을 경우,
			ImageIcon icon = new ImageIcon(imgFilePath); // 이미지 받아와서 아이콘 생성
			changeImage = icon.getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH); // 이미지 사이즈 조정
		}
		ImageIcon changeIcon = new ImageIcon(changeImage); // 사이즈조정한 이미지로 아이콘 재생성
		lblPic.setIcon(changeIcon); // 레이블에 아이콘 달기
	}

	private void initialize() {
		setBorder(new TitledBorder(null, "사원 세부 정보", TitledBorder.LEADING,TitledBorder.TOP, null, null));
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

		JLabel lblEmpno = new JLabel("사원번호");
		lblEmpno.setHorizontalAlignment(SwingConstants.RIGHT);
		pContent.add(lblEmpno);

		tfEmpno = new JTextField();
		tfEmpno.setEditable(false); // 사원번호는 수정불가하게 비활성화
		pContent.add(tfEmpno);
		tfEmpno.setColumns(10);

		JLabel lblHireDate = new JLabel("입사일");
		lblHireDate.setHorizontalAlignment(SwingConstants.RIGHT);
		pContent.add(lblHireDate);

		dateHire = new JDateChooser(new Date());	//선택 전에는 오늘 날짜로 초기화
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

		rdbtnMale = new JRadioButton("남자");
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

		lblConfirm = new JLabel();
		lblConfirm.setFont(new Font("굴림", Font.BOLD, 20));
		lblConfirm.setForeground(Color.RED);
		lblConfirm.setHorizontalAlignment(SwingConstants.CENTER);
		pContent.add(lblConfirm);
	}

	public void setTfEmpno(Employee employee) {
		tfEmpno.setText(String.valueOf(employee.getEmpno()));
	}

	@Override
	public void setItem(EmployeeDetail item) {
		tfEmpno.setText(String.valueOf(item.getEmpNo()));
		byte[] iconBytes = item.getPic();
		ImageIcon icon = new ImageIcon(iconBytes);
		lblPic.setIcon(icon);
		dateHire.setDate(item.getHireDate());
		if (item.isGender()) {
			rdbtnFemale.setSelected(true);
		} else {
			rdbtnMale.setSelected(true);
		}

	}

	@Override
	public EmployeeDetail getItem() {
		validCheck();
		int empNo = Integer.parseInt(tfEmpno.getText().trim());
		boolean gender = rdbtnFemale.isSelected() ? true : false; // 삼항연산자 사용
		Date hireDate = dateHire.getDate();
		String pass = String.valueOf(pfPass1.getPassword());
		byte[] pic = getImage();
		return new EmployeeDetail(empNo, gender, hireDate, pass, pic);
	}

	private byte[] getImage() {
		//load된 이미지를 DB에 반영 > 
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			ImageIcon icon = (ImageIcon) lblPic.getIcon(); // 아이콘을 이미지 아이콘으로 형변환해준다.
			BufferedImage bi = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_RGB); // 임시공간을
																															// 만들어주겠다.
			// 레이블에 있는 사진을 이미지로 만들어주는 코드
			Graphics2D g2 = bi.createGraphics(); // 임시공간에서 읽어와서 이미지를 만들겠다
			g2.drawImage(icon.getImage(), 0, 0, null);
			g2.dispose();

			ImageIO.write(bi, "png", baos);
			return baos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void validCheck() {
		//비밀번호와 비밀번호 확인 비교 후 불일치시, 비밀번호 불일치 예외처리
		if (!lblConfirm.getText().equals("일치")) {
			throw new InvalidCheckException("비밀번호 불일치");
		}
	}

	@Override
	public void clearTf() {
		//이미지파일 선택안된 상태로 초기화 > noImage가 들어가있다.
		//날짜는 오늘 날짜로 초기화
		//성별은 여자선택으로 초기화
		//비밀번호 텍스트필드 공백 초기화
		//일치, 불일치 확인하는 레이블 공백 초기화
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
	//사진 추가 버튼을 눌렀을 때 사진 파일을 찾을 수 있도록 JFileChooser가 뜨도록 하는 메소드
	protected void actionPerformedBtnAddPic(ActionEvent arg0) {
		// 파일 필터 객체 생성 > 노출되고자하는 파일의 유형을 결정한다.
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & PNG & GIF images", "jpg", "png", "gif"); // 설명과 확장자
																														
		chooserPath.setFileFilter(filter);	//파일 필터 적용
		chooserPath.setFileSelectionMode(JFileChooser.FILES_ONLY);	// 디렉토리와 파일 중 파일만 선택할 수 있도록 설정

		// 열기 다이얼로그 출력
		int res = chooserPath.showOpenDialog(null);
		//파일선택 하지 않고 창닫기, 혹은 취소했을 때
		if (res != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(null, "파일을 선택하지 않았습니다.", "경고", JOptionPane.WARNING_MESSAGE);
			return;
		}
		//선택된 파일 경로 가져오기
		String chooseFilePath = chooserPath.getSelectedFile().getPath();
		//사진 설정해주는 메소드의 매개변수로 경로 넘겨주기
		loadPic(chooseFilePath);
	}
	
	//비밀번호 일치, 불일치 비교하는 메소드
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
			// 입력된 비밀번호 텍스트 필드값으로 String 객체 생성 후 비교
			String pass1 = new String(pfPass1.getPassword());
			String pass2 = new String(pfPass2.getPassword());

			if (pass1.equals(pass2)) {
				lblConfirm.setText("일치");
			} else {
				lblConfirm.setText("불일치");
			}
		}
	};

}
