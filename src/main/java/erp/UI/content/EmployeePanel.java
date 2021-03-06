package erp.UI.content;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import erp.Service.EmployeeService;
import erp.UI.exception.InvalidCheckException;
import erp.dto.Department;
import erp.dto.Employee;
import erp.dto.Title;

@SuppressWarnings("serial")
public class EmployeePanel extends AbstractContentPanel<Employee> implements ItemListener {
	private JTextField tfEmpno;
	private JTextField tfEmpname;
	private JComboBox<Title> cmbTitle; // 콤보박스에 지네릭을 설정해줘야 연동하기 쉽다. 그렇지 않으면 String으로 추가해줘야해서 번거롭다
	private JComboBox<Employee> cmbManager;
	private JSpinner spinnerSalary;
	private JComboBox<Department> cmbDept;

	private EmployeeService service;

	public EmployeePanel() {

		initialize();
	}

	public void setService(EmployeeService service) {
		
		this.service = service;
		
		//<부서 콤보박스 연동>
		// service객체를 통해 호출된 부서를 모두 가져오는 select 수행하는 메소드 호출해서 List로 받아오기
		// 반환받은 리스트를 조건에 맞춰 (리스트 > 벡터 > 모델)
		// 콤보박스에 모델 달아주기
		// 기본적으로 선택 전에는 보이지 않게 설정
		List<Department> deptList = service.showDeptList();
		DefaultComboBoxModel<Department> deptModel = new DefaultComboBoxModel<>(new Vector<>(deptList));
		cmbDept.setModel(deptModel);
		cmbDept.setSelectedIndex(-1); 
		
		//<직책 콤보박스 연동>
		//service객체를 통해 호출된 직책을 모두 가져오는 select 수행하는 메소드를 호출해서 List로 받아오기
		// 반환받은 리스트를 벡터로 생성 후, 모델을 생성해준다.
		//콤보박스에 모델 달아주기
		// 기본적으로 선택 전에는 보이지 않게 설정
		List<Title> titleList = service.showTitleList();
		DefaultComboBoxModel<Title> titleModel = new DefaultComboBoxModel<>(new Vector<>(titleList));
		cmbTitle.setModel(titleModel);
		cmbTitle.setSelectedIndex(-1);

		//<직속상사 콤보박스 연동> > 부서가 먼저 선택되면 그 부서의 사원들만 직속상사 콤보박스에 보이는게 좋지 않을까? > 부서 콤보박스 선택되면 수행되는 메소드에 코드 작성
//		List<Employee> managerList = service.showEmployeeListByDept((Department) cmbDept.getSelectedItem());;
//		DefaultComboBoxModel<Employee> managerModel = new DefaultComboBoxModel<>(new Vector<>(managerList));
//		cmbManager.setModel(managerModel);


	}

	public void initialize() {
		setBorder(new TitledBorder(null, "사원정보", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new BorderLayout(0, 0));

		JPanel pItem = new JPanel();
		add(pItem);
		pItem.setLayout(new GridLayout(0, 2, 10, 10));

		JLabel lblEmpno = new JLabel("사원번호");
		lblEmpno.setHorizontalAlignment(SwingConstants.TRAILING);
		pItem.add(lblEmpno);

		tfEmpno = new JTextField();
		tfEmpno.setColumns(10);
		pItem.add(tfEmpno);

		JLabel lblEmpname = new JLabel("사원명");
		lblEmpname.setHorizontalAlignment(SwingConstants.TRAILING);
		pItem.add(lblEmpname);

		tfEmpname = new JTextField();
		tfEmpname.setColumns(10);
		pItem.add(tfEmpname);

		JLabel lblDept = new JLabel("부서");
		lblDept.setHorizontalAlignment(SwingConstants.TRAILING);
		pItem.add(lblDept);

		cmbDept = new JComboBox<>();
		cmbDept.addItemListener(this);
		pItem.add(cmbDept);

		JLabel lblManager = new JLabel("직속상사");
		lblManager.setHorizontalAlignment(SwingConstants.TRAILING);
		pItem.add(lblManager);

		cmbManager = new JComboBox<>();
		pItem.add(cmbManager);

		JLabel lblTitle = new JLabel("직책");
		lblTitle.setHorizontalAlignment(SwingConstants.TRAILING);
		pItem.add(lblTitle);

		cmbTitle = new JComboBox<>();
		pItem.add(cmbTitle);

		JLabel lblSalary = new JLabel("급여");
		lblSalary.setHorizontalAlignment(SwingConstants.TRAILING);
		pItem.add(lblSalary);

		spinnerSalary = new JSpinner();
		// 기본값 200, 최소값 150, 최대값 500, 단위 10
		spinnerSalary.setModel(new SpinnerNumberModel(2000000, 1500000, 5000000, 100000));
		pItem.add(spinnerSalary);

	}

	public void itemStateChanged(ItemEvent arg0) {
		if (arg0.getSource() == cmbDept) {
			itemStateChangedCmbDept(arg0);
		}
	}
	
	//부서 콤보박스에서 선택이 되었을 때 동작하는 메소드
	protected void itemStateChangedCmbDept(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			
			//cmbDept에서 선택된 부서의 Department 가져오기
			Department selDept = (Department) cmbDept.getSelectedItem();
			//선택된 부서에 속하는 사원들을 select하는 메소드를 호출하고 List로 받아온다.
			List<Employee> managerList = service.showEmployeeListByDept(selDept);
			
			// 해당부서에 사원이 없을 경우(직속상사 콤보박스에 가져올 사원이 없다면 빈 List 생성)
			if (managerList == null) {
				managerList = new ArrayList<>();
			}
			//선택된 부서에 속하는 사원들의 List로 벡터를 생성 후, 모델 생성
			DefaultComboBoxModel<Employee> managerModel = new DefaultComboBoxModel<>(new Vector<>(managerList));
			//직속상사 콤보박스에 모델 달아주기
			cmbManager.setModel(managerModel);
			// 기본적으로 선택 전에는 보이지 않게 설정
			cmbManager.setSelectedIndex(-1);
		}

	}

	@Override
	public void setItem(Employee item) {
		// set하고자 하는 Employee객체의 각 필드 중 사용자 지정 타입일 때(Title, Department, Employee)
		// 받아오는 매개변수와 비교할 수 있도록 dto에 equals를 생성해줘야한다.
		tfEmpname.setText(item.getEmpname());
		tfEmpno.setText(item.getEmpno() + "");
		cmbTitle.setSelectedItem(item.getTitle()); // title dto에 가서 title tno를 equals를 생성해줘야한다.
		cmbDept.setSelectedItem(item.getDept());
		cmbManager.setSelectedItem(item.getManager());
		spinnerSalary.setValue(item.getSalary());

	}

	@Override
	public Employee getItem() {
		// 유효성 검사
		validCheck();
		// 텍스트 필드에 입력된 부분을 String 형태로 변환 후 가져오기
		int empno = Integer.parseInt(tfEmpno.getText().trim());
		String empname = tfEmpname.getText().trim();
		// 콤보박스에서 선택된 부분을 객체의 형태로 변환 후 가져오기
		Title title = (Title) cmbTitle.getSelectedItem(); // 선택된 객체를 가져온다.
		Employee manager = (Employee) cmbManager.getSelectedItem();
		int salary = (int) spinnerSalary.getValue();
		Department dept = (Department) cmbDept.getSelectedItem();
		return new Employee(empno, empname, title, manager, salary, dept);
	}

	@Override
	public void validCheck() {
		// 유효성 검사 : 텍스트 필드가 공백이거나 콤보박스에 선택된 항목이 없으면 입력을 유도
		if (tfEmpno.getText().contentEquals("") || tfEmpname.getText().contentEquals("")
				|| (cmbDept.getSelectedIndex() == -1) || (cmbManager.getSelectedIndex() == -1)
				|| (cmbTitle.getSelectedIndex() == -1)) {
			throw new InvalidCheckException();
		}

	}

	@Override
	public void clearTf() {
		tfEmpno.setText("");
		tfEmpname.setText("");
		cmbDept.setSelectedIndex(-1);
		cmbTitle.setSelectedIndex(-1);
		cmbManager.setSelectedIndex(-1);
		spinnerSalary.setValue(1500000);
	}

}
