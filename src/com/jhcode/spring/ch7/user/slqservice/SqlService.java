package com.jhcode.spring.ch7.user.slqservice;

import com.jhcode.spring.ch7.user.slqservice.exception.SqlRetrievalFailureException;

public interface SqlService {
	String getSql(String key) throws SqlRetrievalFailureException;
}
