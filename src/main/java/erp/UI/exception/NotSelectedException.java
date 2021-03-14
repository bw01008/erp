package erp.UI.exception;

@SuppressWarnings("serial")
public class NotSelectedException extends RuntimeException {
	//디폴트 생성자와 Throwable생성자
	public NotSelectedException() {
		super("목록을 선택하세요.");
	}

	public NotSelectedException(Throwable cause) {
		super("목록을 선택하세요.", cause);
	}
	
	

}
