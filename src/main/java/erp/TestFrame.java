package erp;

import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import erp.UI.List.DepartmentTablePanel;
import erp.UI.List.TitleTablePanel;

@SuppressWarnings("serial")
public class TestFrame extends JFrame {

	private JPanel contentPane;
	
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
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));
		setContentPane(contentPane);
		
		TitleTablePanel pTitle = new TitleTablePanel();
		pTitle.loadData(); // TitleTablePanel의 생성자에서 안되어서 여기서 해줌
		contentPane.add(pTitle);
		
		DepartmentTablePanel pDept = new DepartmentTablePanel();
		pDept.loadData();
		contentPane.add(pDept);
	}

}
