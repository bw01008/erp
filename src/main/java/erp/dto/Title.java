package erp.dto;

//1. Title테이블을 클래스로 생성

public class Title {
	//Field
	private int tno;
	private String tname;

	//Constructor
	//기본 생성자
	public Title() {
	}
	// 기본키를 매개변수로 받는 생성자
	public Title(int tno) {
		this.tno = tno;
	}
	// 테이블의 모든 칼럼을 매개변수로 받는 생성자
	public Title(int tno, String tname) {
		this.tno = tno;
		this.tname = tname;
	}
	
	//Method

	public int getTno() {
		return tno;
	}

	public void setTno(int tno) {
		this.tno = tno;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + tno;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Title other = (Title) obj;
		if (tno != other.tno)
			return false;
		return true;
	}
	//toString Override
	@Override
	public String toString() {
		return String.format("%s(%d)", tname, tno);
	}

}
