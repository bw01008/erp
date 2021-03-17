package erp.UI.List;

import javax.swing.SwingConstants;

import erp.Service.DepartmentService;
import erp.UI.exception.NotSelectedException;
import erp.dto.Department;
import erp.dto.Title;

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
		//Object타입의 배열 > 배열 안에 요소들은 참조형이다.(각 요소가 모두 객체다.)
		//t.getTno()(int형) > INTEGER클래스로 오토박싱되어 들어간다.
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

	@Override
	public Department getItem() {
		int row = table.getSelectedRow(); //선택된 로우의 인덱스 넘버를 반환
		int deptNo = (int) table.getValueAt(row, 0);//(열, 행) > 선택한 열의 가장 앞(0)의 컬럼(titleNo - 기본키)를 가져온다(INTEGER형 객체) > int형으로 형변환해줘야한다.
//		list.indexOf(new Department(deptNo));
		if (row == -1) {// 만약 선택이 안되면 -1을 리턴한다
			throw new NotSelectedException();
		}
		return list.get(list.indexOf(new Department(deptNo)));	//그 인덱스에 위치한 객체를 반환한다.
	}

}
