package erp.UI.exception;

@SuppressWarnings("serial")
public class SqlConstraintException extends RuntimeException {
	// sql에서 실행되지 않는(제약조건으로 막아놓은) 사항들을 자바에서 시도했을 때 발생되는 예외를 처리
	// 외래키 참조조건
	// 기본키 중복불가 
	
	public SqlConstraintException() {
		super("참조하는 레코드가 존재합니다.");
	}

	public SqlConstraintException(String message) {
		super(message);
	}

	public SqlConstraintException(Throwable cause) {
		super("참조하는 레코드가 존재합니다.", cause);
	}

	public SqlConstraintException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
