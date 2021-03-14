package erp.UI.List;

import javax.swing.SwingConstants;

import erp.Service.DepartmentService;
import erp.dto.Department;

@SuppressWarnings("serial")
public class DepartmentTablePanel extends AbstractCustomTablePanel<Department> {

	private DepartmentService service; // 서비스 선언

	@Override
	protected void setAlignAndWidth() {
		// 컬럼 내용 정렬
		setTableCellAlign(SwingConstants.CENTER, 0, 1);
		// 컬럼별 너비 조정
		setTableCellWidth(100, 250);

	}

	@Override
	public String[] getColumnNames() {
		return new String[] { "부서번호", "부서명", "위치" };
	}

	@Override
	public Object[] toArray(Department t) {

		return new Object[] { t.getDeptNo(), t.getDeptName(), t.getFloor() };
	}
	
	@Override
	public void initList() {
		list = service.showDepartment(); // 모든 데이터를 리스트에 담아온다.
	}

	// 여기에서(panel에서) service객체를 생성하지 않고 선언만 해준이유?
	// 가장 최상위인 jframe에서(DepartmentManager에서) service객체를 만들고,
	//몹핑된 패널들이 그 객체를 사용할 수 있도록 메소드를 만들어준다.(코드 중복을 피하기 위해)
	public void setService(DepartmentService service) {
		this.service = service;	 
	}

}
