package erp;

import java.awt.EventQueue;
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
import erp.UI.content.EmpPanel;
import erp.dto.Department;
import erp.dto.Employee;
import erp.dto.Title;

@SuppressWarnings("serial")
public class TestFrame extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JButton btnAdd;
	private EmpPanel pEmp;
	private JButton btnCancel;
	private JButton btnSet;
	private EmployeeService service;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestFrame frame = new TestFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public TestFrame() {
		initialize();
	}

	private void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 822, 769);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

//		TitleTablePanel pTitle = new TitleTablePanel();
//		pTitle.loadData(); // TitleTablePanel의 생성자에서 안되어서 여기서 해줌
//		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
//		contentPane.add(pTitle);
//		
//		DepartmentTablePanel pDept = new DepartmentTablePanel();
//		pDept.loadData();
//		contentPane.add(pDept);

		pEmp = new EmpPanel();
		service = new EmployeeService();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		pEmp.setService(service);
		contentPane.add(pEmp);

		JPanel pBtn = new JPanel();
		contentPane.add(pBtn);

		btnAdd = new JButton("추가");
		btnAdd.addActionListener(this);
		pBtn.add(btnAdd);

		btnCancel = new JButton("취소");
		btnCancel.addActionListener(this);
		
		btnSet = new JButton("set");
		btnSet.addActionListener(this);
		pBtn.add(btnSet);
		pBtn.add(btnCancel);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnSet) {
			actionPerformedBtnSet(e);
		}
		if (e.getSource() == btnCancel) {
			actionPerformedBtnCancel(e);
		}
		if (e.getSource() == btnAdd) {
			actionPerformedBtnAdd(e);
		}
	}

	protected void actionPerformedBtnAdd(ActionEvent e) {
		Employee emp = pEmp.getEmp();
		List<Employee> empList = service.showEmployeeList();
		System.out.println(empList);
		if (empList.contains(emp)) {
			JOptionPane.showMessageDialog(null, "이미존재하는 사원입니다.");
		} else {
			empList.add(emp);
			JOptionPane.showMessageDialog(null, emp + "이/가 사원 목록에 추가되었습니다.");
		}

	}

	protected void actionPerformedBtnSet(ActionEvent e) {
		//객체 생성시, 사용자 지정타입의 객체를 매개변수로 할 때 비교가 필요하면 dto에 equals를 생성해줘야한다 
		Employee emp = new Employee(1003, "조민희", new Title(3), new Employee(4377), 3000000, new Department(2));
		pEmp.setEmp(emp);
	}
	
	protected void actionPerformedBtnCancel(ActionEvent e) {
		pEmp.clearTf();
	}

}
