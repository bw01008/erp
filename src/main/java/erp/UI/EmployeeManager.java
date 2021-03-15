package erp.UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import erp.Service.EmployeeService;
import erp.UI.List.EmployeeTablePanel;
import erp.UI.content.EmpPanel;
import erp.dto.Employee;

@SuppressWarnings("serial")
public class EmployeeManager extends JFrame implements ActionListener {

	private JPanel contentPane;
	private EmployeeService service;
	private JButton btnAdd;
	private EmpPanel pContent;
	private JPanel pBtn;
	private EmployeeTablePanel pList;

	public EmployeeManager() {
		service = new EmployeeService();
		initialize();
	}
	private void initialize() {
		setTitle("사원관리");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 529);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		pContent = new EmpPanel();
		pContent.setService(service);
		contentPane.add(pContent);
		
		pBtn = new JPanel();
		contentPane.add(pBtn);
		
		btnAdd = new JButton("추가");
		btnAdd.addActionListener(this);
		pBtn.add(btnAdd);
		
		JButton btnCancel = new JButton("취소");
		pBtn.add(btnCancel);
		
		pList = new EmployeeTablePanel();
		contentPane.add(pList);
		pList.setService(service);
		pList.loadData();
		
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnAdd) {
			actionPerformedBtnAdd(e);
		}
	}
	protected void actionPerformedBtnAdd(ActionEvent e) {
		Employee newEmp = pContent.getEmp();
		List<Employee> empList = service.showEmployeeList();
		if(empList.contains(newEmp)) {
			JOptionPane.showMessageDialog(null, newEmp + "이미 존재하는 사원입니다.");
		}else {
			service.addEmployee(newEmp);
			pList.loadData();	//추가 후 loadData()메소드를 호출해야 추가사항이 반영된다.
			pContent.clearTf();
			JOptionPane.showMessageDialog(null, newEmp.getEmpname() + "이 추가되었습니다.");
		}
	}
}
