package com.jhcode.spring.ch7.user.slqservice;

import java.util.HashMap;
import java.util.Map;

import com.jhcode.spring.ch7.user.slqservice.exception.SqlNotFoundException;
import com.kitec.springframe.ch7.study2_6.springframe.sqlservice.SqlRetrievalFailureException;

public class HashMapSqlRegistry implements SqlRegistry {
	//== SqlRegistry 구현부를 분리 ==//
	private Map<String, String> sqlMap = new HashMap<String, String>();

	@Override
	public String findSql(String key) throws SqlNotFoundException {
		String sql = sqlMap.get(key);
		if (sql == null) throw new SqlRetrievalFailureException(key + ": SQL을 찾을 수 없습니다.");
		else return sql;
	}
	
	@Override
	public void registerSql(String key, String sql) {
		sqlMap.put(key, sql);
	}
}
