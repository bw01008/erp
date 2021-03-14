package erp.UI.exception;

@SuppressWarnings("serial")
public class InvalidCheckException extends RuntimeException {
	//항목이 비었을때 발생하는 예외를 처리
	public InvalidCheckException() {
		super("공백이 존재합니다.");
	}

	public InvalidCheckException(Throwable cause) {
		super("공백이 존재합니다.", cause);
	}
	
}
