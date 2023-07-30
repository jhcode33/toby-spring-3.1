package com.jhcode.spring.ch7.user.slqservice;

import com.jhcode.spring.ch7.user.slqservice.exception.SqlNotFoundException;

public interface SqlRegistry {
	
	void registerSql(String key, String sql);
	
	String findSql(String key) throws SqlNotFoundException;

}
