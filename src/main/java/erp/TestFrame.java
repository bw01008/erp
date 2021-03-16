package erp;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import erp.Service.EmployeeService;
import erp.UI.List.EmployeeTablePanel;
import erp.UI.content.EmployeePanel;
import erp.dto.Department;
import erp.dto.Employee;
import erp.dto.Title;

@SuppressWarnings("serial")
public class TestFrame extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JButton btnAdd;

	private EmployeePanel pEmpItem;
	private JButton btnSet;
	private JButton btnCancel;
	private EmployeeTablePanel pList;
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
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

		service = new EmployeeService();
//		TitleTablePanel pTitle = new TitleTablePanel();
//		pTitle.loadData(); // TitleTablePanel의 생성자에서 안되어서 여기서 해줌
//		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
//		contentPane.add(pTitle);
//		
//		DepartmentTablePanel pDept = new DepartmentTablePanel();
//		pDept.loadData();
//		contentPane.add(pDept);

		pEmpItem = new EmployeePanel();
		pEmpItem.setService(service);
		contentPane.add(pEmpItem);

		JPanel pBtn = new JPanel();
		contentPane.add(pBtn);

		btnAdd = new JButton("추가");
		btnAdd.addActionListener(this);
		pBtn.add(btnAdd);
		
		btnSet = new JButton("set");
		btnSet.addActionListener(this);
		pBtn.add(btnSet);

		btnCancel = new JButton("취소");
		btnCancel.addActionListener(this);
		pBtn.add(btnCancel);
		
		pList = new EmployeeTablePanel();
		pList.setService(service);
		pList.loadData();
		contentPane.add(pList);

	}

	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource() == btnSet) {
				actionPerformedBtnSet(e);
			}
			if (e.getSource() == btnCancel) {
				actionPerformedBtnCancel(e);
			}
			if (e.getSource() == btnAdd) {
				actionPerformedBtnAdd(e);
			}
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage());
			e1.printStackTrace();
		}
	}

	protected void actionPerformedBtnAdd(ActionEvent e) {
		Employee emp = pEmpItem.getItem();
		String message = String.format(
				"empNo %d%n" + "empName %s%n" + "title(%d)%n" + "dept(%d)%n" + "manager(%s)%n" + "salary(%s)",
				emp.getEmpno(), emp.getEmpname(), emp.getTitle().getTno(), emp.getDept().getDeptNo(),
				emp.getManager().getEmpname(), emp.getSalary());
		JOptionPane.showMessageDialog(null, message);

	}

	protected void actionPerformedBtnSet(ActionEvent e) {
		// 객체 생성시, 사용자 지정타입의 객체를 매개변수로 할 때 비교가 필요하면 dto에 equals를 생성해줘야한다
		Employee emp = new Employee(1003, "조민희", new Title(3), new Employee(4377), 3000000, new Department(2));
		pEmpItem.setItem(emp);
	}

	protected void actionPerformedBtnCancel(ActionEvent e) {
		pEmpItem.clearTf();
	}

}
