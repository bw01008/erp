package erp.dto;

//1. Employee테이블을 클래스로 생성

public class Employee {
	// Fields
	private int empno;
	private String empname;
	private Title title;
	private Employee manager; 
	private int salary;
	private Department dept; 

	// Constructor
	//기본 생성자
	public Employee() {
		
	}

	//기본키를 매개변수로 받는 생성자
	public Employee(int empno) {
		this.empno = empno;
	}
	
	
	
	public Employee(int empno, String empname) {
		this.empno = empno;
		this.empname = empname;
	}

	//데이터 칼럼을 모두 매개변수로 받는 생성자
	public Employee(int empno, String empname, Title title, Employee manager, int salary, Department dept) {
		this.empno = empno;
		this.empname = empname;
		this.title = title;
		this.manager = manager;
		this.salary = salary;
		this.dept = dept;
	}

	//Method
	public int getEmpno() {
		return empno;
	}

	public void setEmpno(int empno) {
		this.empno = empno;
	}

	public String getEmpname() {
		return empname;
	}

	public void setEmpname(String empname) {
		this.empname = empname;
	}

	public Title getTitle() {
		return title;
	}

	public void setTitle(Title title) {
		this.title = title;
	}

	public Employee getManager() {
		return manager;
	}

	public void setManager(Employee manager) {
		this.manager = manager;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public Department getDept() {
		return dept;
	}

	public void setDept(Department dept) {
		this.dept = dept;
	}

	
	@Override
	public String toString() {
		return String.format("Employee [empno=%s, empname=%s]", empno, empname);
	}
	
	

}
