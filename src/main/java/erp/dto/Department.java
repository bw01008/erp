package erp.dto;

import java.util.List;

// 1. department 테이블을 클래스로 생성
public class Department {
	// field
	private int deptNo;
	private String deptName;
	private int floor;
	private List<Employee> emplist;

	// Constructor
	// 모든 필드를 매개변수로 받는 생성자
	public Department(int deptNo, String deptName, int floor) {
		this.deptNo = deptNo;
		this.deptName = deptName;
		this.floor = floor;
	}
	// 기본키를 매개변수로 받는 생성자
	public Department(int deptNo) {
		this.deptNo = deptNo;
	}

	// 기본 생성자
	public Department() {

	}

	// getter & setter
	public int getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(int deptNo) {
		this.deptNo = deptNo;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	// toString
	@Override
	public String toString() {
		return String.format("Department [deptNo=%s, deptName=%s, floor=%s]", deptNo, deptName, floor);
	}

}
