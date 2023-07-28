package com.jhcode.spring.ch7.user.slqservice;

public interface SqlService {
	// 런타임 예외를 던지도록 설정한다
	String getSql(String key) throws SqlRetrievalFailureException;
}
