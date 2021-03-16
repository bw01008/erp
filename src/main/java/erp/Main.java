package erp;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import erp.UI.DepartmentManagerUI;
import erp.UI.EmployeeManagerUI;
import erp.UI.TitleManagerUI;

@SuppressWarnings("serial")
public class Main extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JButton btnTitle;
	private JButton btnDepartment;
	private JButton btnEmployee;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		initialize();
	}
	private void initialize() {
		setTitle("emp Management");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 125);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 3, 0, 0));
		
		btnTitle = new JButton("직책관리");
		btnTitle.addActionListener(this);
		contentPane.add(btnTitle);
		
		btnDepartment = new JButton("부서관리");
		btnDepartment.addActionListener(this);
		contentPane.add(btnDepartment);
		
		btnEmployee = new JButton("사원관리");
		btnEmployee.addActionListener(this);
		contentPane.add(btnEmployee);
	}

	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == btnDepartment) {
			actionPerformedBtnDepartment(arg0);
		}
		if (arg0.getSource() == btnTitle) {
			actionPerformedBtnTitle(arg0);
		}
		if (arg0.getSource() == btnEmployee) {
			actionPerformedBtnEmployee(arg0);
		}
	}
	protected void actionPerformedBtnTitle(ActionEvent arg0) {
		TitleManagerUI frame = new TitleManagerUI();
		frame.setTitle("직책 관리");
		frame.setVisible(true);
	}
	protected void actionPerformedBtnDepartment(ActionEvent arg0) {
		DepartmentManagerUI frame = new DepartmentManagerUI();
		frame.setTitle("부서 관리");
		frame.setVisible(true);
	}
	protected void actionPerformedBtnEmployee(ActionEvent arg0) {
		EmployeeManagerUI frame = new EmployeeManagerUI();
		frame.setTitle("사원 관리");
		frame.setVisible(true);
	}

}
