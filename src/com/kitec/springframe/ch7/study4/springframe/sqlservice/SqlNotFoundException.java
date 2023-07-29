package com.kitec.springframe.ch7.study4.springframe.sqlservice;

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
