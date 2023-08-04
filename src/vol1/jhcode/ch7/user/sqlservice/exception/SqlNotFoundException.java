package vol1.jhcode.ch7.user.sqlservice.exception;

public class SqlNotFoundException extends RuntimeException {
	public SqlNotFoundException() {
		super();
	}

	public SqlNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public SqlNotFoundException(String message) {
		super(message);
	}

	public SqlNotFoundException(Throwable cause) {
		super(cause);
	}

}
