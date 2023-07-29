package com.kitec.springframe.ch7.study4.springframe.sqlservice;

public interface SqlRegistry {
	void registerSql(String key, String sql);

	String findSql(String key) throws SqlNotFoundException;
}
