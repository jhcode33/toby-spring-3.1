package com.kitec.springframe.ch7.study4.springframe.sqlservice;

public interface SqlService {
	String getSql(String key) throws SqlRetrievalFailureException;
}
