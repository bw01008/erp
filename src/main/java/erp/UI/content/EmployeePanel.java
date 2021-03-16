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
		// service객체를 통해 호출된 메소드로 반환받은 리스트를 조건에 맞춰 (리스트 > 벡터 > 모델)
		List<Department> deptList = service.showDeptList();
		DefaultComboBoxModel<Department> deptModel = new DefaultComboBoxModel<>(new Vector<>(deptList));
		cmbDept.setModel(deptModel);

		List<Title> titleList = service.showTitleList();
		DefaultComboBoxModel<Title> titleModel = new DefaultComboBoxModel<>(new Vector<>(titleList));
		cmbTitle.setModel(titleModel);

//		List<Employee> managerList = service.showEmployeeListByDept((Department) cmbDept.getSelectedItem());;
//		DefaultComboBoxModel<Employee> managerModel = new DefaultComboBoxModel<>(new Vector<>(managerList));
//		cmbManager.setModel(managerModel);

		cmbDept.setSelectedIndex(-1); // 기본적으로 선택전에는 보이지 않게 설정
		cmbTitle.setSelectedIndex(-1);

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
	
	@Override
	public void clearTf() {
		tfEmpno.setText("");
		tfEmpname.setText("");
		cmbDept.setSelectedIndex(-1);
		cmbTitle.setSelectedIndex(-1);
		cmbManager.setSelectedIndex(-1);
		spinnerSalary.setValue(1500000);
	}

	public void itemStateChanged(ItemEvent arg0) {
		if (arg0.getSource() == cmbDept) {
			itemStateChangedCmbDept(arg0);
		}
	}

	protected void itemStateChangedCmbDept(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			Department selDept = (Department) cmbDept.getSelectedItem();
			List<Employee> managerList = service.showEmployeeListByDept(selDept);
			//직속 상사가 없는 경우 추가
			if(managerList == null) {
				managerList = new ArrayList<>();
			}
			DefaultComboBoxModel<Employee> managerModel = new DefaultComboBoxModel<>(new Vector<>(managerList));
			cmbManager.setModel(managerModel);
			cmbManager.setSelectedIndex(-1);
		}

	}

	@Override
	public void setItem(Employee item) {
		//set하고자 하는 Employee객체의 각 필드 중 사용자 지정 타입일때(Title, Department, Employee)
		//받아오는 매개변수와 비교할 수 있도록 dto에 equals를 생성해줘야한다.
		tfEmpname.setText(item.getEmpname());
		tfEmpno.setText(item.getEmpno() + "");
		cmbTitle.setSelectedItem(item.getTitle()); //title dto에 가서 title tno를 equals를 생성해줘야한다.
		cmbDept.setSelectedItem(item.getDept());
		cmbManager.setSelectedItem(item.getManager());
		spinnerSalary.setValue(item.getSalary());

	}

	@Override
	public Employee getItem() {
		validCheck();
		int empno = Integer.parseInt(tfEmpno.getText().trim());
		String empname = tfEmpname.getText().trim();
		Title title = (Title) cmbTitle.getSelectedItem(); // 선택된 객체를 가져온다.
		Employee manager = (Employee) cmbManager.getSelectedItem();
		int salary = (int) spinnerSalary.getValue();
		Department dept = (Department) cmbDept.getSelectedItem();
		return new Employee(empno, empname, title, manager, salary, dept);
	}

	@Override
	public void validCheck() {
		if (tfEmpno.getText().contentEquals("") || tfEmpname.getText().contentEquals("")
				|| (cmbDept.getSelectedIndex() == -1) 
				|| (cmbManager.getSelectedIndex() == -1)
				|| (cmbTitle.getSelectedIndex() == -1)) {
			throw new InvalidCheckException();
		}

	}


}
