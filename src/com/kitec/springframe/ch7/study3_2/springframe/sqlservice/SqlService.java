package com.kitec.springframe.ch7.study3_2.springframe.sqlservice;

public interface SqlService {
	String getSql(String key) throws SqlRetrievalFailureException;
}
