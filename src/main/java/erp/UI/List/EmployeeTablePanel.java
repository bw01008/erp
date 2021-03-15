package erp.UI.List;

import javax.swing.SwingConstants;

import erp.Service.EmployeeService;
import erp.dto.Employee;

@SuppressWarnings("serial")
public class EmployeeTablePanel extends AbstractCustomTablePanel<Employee> {
	
	private EmployeeService service;

	@Override
	public void initList() {
		list = service.showEmployeeList();
	}

	public void setService(EmployeeService service) {
		this.service = service;
	}

	@Override
	public String[] getColumnNames() {
		return new String[] { "사원번호", "사원명", "부서", "직책", "직속상사", "급여" };

	}

	@Override
	public Object[] toArray(Employee t) {
		return new Object[] { t.getEmpno(), 
				t.getEmpname(), 
				String.valueOf(t.getDept().getDeptName()),
				String.valueOf(t.getTitle().getTname()), 
				String.valueOf(t.getManager().getEmpname()),
				(t.getSalary() + "") };
	}

	@Override
	protected void setAlignAndWidth() {
		// 컬럼 내용 정렬
		setTableCellAlign(SwingConstants.CENTER, 0, 1);
		// 컬럼별 너비 조정
		setTableCellWidth(100, 250);
	}

}
