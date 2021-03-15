package erp.UI.List;

import javax.swing.SwingConstants;

import erp.Service.TitleService;
import erp.dto.Title;

// 추상 클래스 AbstractCustomTablePanel(패널)을 상속받은 TitleTablePanel (일반)클래스
@SuppressWarnings("serial")
public class TitleTablePanel extends AbstractCustomTablePanel<Title> {

	private TitleService service; // 따로 service객체를 생성해주지 않는다 > jframe에 생성된 객체를 사용하면된다.

	@Override
	public void initList() {
		list = service.showTitle();

	}

	// 여기에서(panel에서) service객체를 생성하지 않고 선언만 해준이유?
	// 가장 최상위인 jframe에서(DepartmentManager에서) service객체를 만들고,
	// 몹핑된 패널들이 그 객체를 사용할 수 있도록 메소드를 만들어준다.(코드 중복을 피하기 위해)
	public void setService(TitleService service) {
		this.service = service;
	}



	@Override
	public String[] getColumnNames() {
		return new String[] { "직책번호", "직책명" };
	}

	@Override
	public Object[] toArray(Title t) {
		return new Object[] { t.getTno(), t.getTname() };
	}
	
	protected void setAlignAndWidth() {

		// 컬럼 내용 정렬
		setTableCellAlign(SwingConstants.CENTER, 0, 1);
		// 컬럼별 너비 조정
		setTableCellWidth(100, 250);

	}

}
