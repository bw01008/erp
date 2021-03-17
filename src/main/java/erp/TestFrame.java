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

import erp.Service.EmployeeDetailService;
import erp.Service.EmployeeService;
import erp.UI.List.EmployeeTablePanel;
import erp.UI.content.EmployeeDetailPanel;
import erp.dto.Employee;
import erp.dto.EmployeeDetail;

@SuppressWarnings("serial")
public class TestFrame extends JFrame implements ActionListener {

	private JPanel contentPane;
	private EmployeeTablePanel pList;
	private EmployeeService service;
	private EmployeeDetailPanel panel;
	private JPanel panel_1;
	private JButton btnGet;
	private JButton btnSet;

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
		setBounds(100, 100, 576, 904);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

		service = new EmployeeService();
		
		pList = new EmployeeTablePanel();
		pList.setService(service);
		pList.loadData();
		contentPane.add(pList);

		
		panel = new EmployeeDetailPanel();
		panel.setTfEmpno(new Employee(1003));
		contentPane.add(panel);
		
		panel_1 = new JPanel();
		contentPane.add(panel_1);
		
		btnGet = new JButton("가져오기");
		btnGet.addActionListener(this);
		panel_1.add(btnGet);
		
		btnSet = new JButton("불러오기");
		btnSet.addActionListener(this);
		panel_1.add(btnSet);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnSet) {
			actionPerformedBtnSet(e);
		}
		if (e.getSource() == btnGet) {
			actionPerformedBtnGet(e);
		}
		try {

		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage());
			e1.printStackTrace();
		}
	}


	protected void actionPerformedBtnGet(ActionEvent e) {
		EmployeeDetail employeeDetail = panel.getItem();
		JOptionPane.showMessageDialog(null, employeeDetail);
	}
	protected void actionPerformedBtnSet(ActionEvent e) {
		EmployeeDetailService service = new EmployeeDetailService();
		EmployeeDetail empDetail = service.selectEmployeeDetailByEmpNo(new Employee(1003));
		panel.setItem(empDetail);
		
	}
}
