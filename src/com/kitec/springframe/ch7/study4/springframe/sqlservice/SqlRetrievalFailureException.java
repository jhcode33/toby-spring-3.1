package com.kitec.springframe.ch7.study4.springframe.sqlservice;

public class SqlRetrievalFailureException extends RuntimeException {
	public SqlRetrievalFailureException() {
		super();
	}

	public SqlRetrievalFailureException(String message) {
		super(message);
	}

	public SqlRetrievalFailureException(Throwable cause) {
		super(cause);
	}

	public SqlRetrievalFailureException(String message, Throwable cause) {
		super(message, cause);
	}

}
