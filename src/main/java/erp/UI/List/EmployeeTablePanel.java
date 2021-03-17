package erp.UI.List;

import javax.swing.SwingConstants;

import erp.Service.EmployeeService;
import erp.UI.exception.NotSelectedException;
import erp.dto.Employee;
import erp.dto.Title;

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
	// Object타입의 배열 > 배열 안에 요소들은 참조형이다.(각 요소가 모두 객체다.)
	// t.getTno()(int형) > INTEGER클래스로 오토박싱되어 들어간다.
	public Object[] toArray(Employee t) {
		return new Object[] { t.getEmpno(), t.getEmpname(), String.valueOf(t.getDept().getDeptName()),
				String.valueOf(t.getTitle().getTname()), String.valueOf(t.getManager().getEmpname()),
				(t.getSalary() + "") };
	}

	@Override
	protected void setAlignAndWidth() {
		// 컬럼 내용 정렬
		setTableCellAlign(SwingConstants.CENTER, 0, 1, 2, 3, 4, 5);
		setTableCellAlign(SwingConstants.RIGHT, 4);
		// 컬럼별 너비 조정
		setTableCellWidth(100, 150, 100, 130, 100, 100);
	}

	@Override
	public Employee getItem() {
		int row = table.getSelectedRow(); // 선택된 로우의 인덱스 넘버를 반환
		int empNo = (int) table.getValueAt(row, 0); // (열, 행) > 선택한 열의 가장 앞(0)의 컬럼(EmpNo - 기본키)를 가져온다(INTEGER형 객체) > int형으로 형변환
//		list.indexOf(new Title(empNo));	//매개변수로 들어간 객체의 위치를 찾는다.
		if (row == -1) {// 만약 선택이 안되면 -1을 리턴한다
			throw new NotSelectedException();
		}
		return list.get(list.indexOf(new Employee(empNo))); //그 인덱스에 위치한 객체를 반환한다.
	}

}
