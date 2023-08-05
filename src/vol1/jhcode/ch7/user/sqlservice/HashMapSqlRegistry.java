package vol1.jhcode.ch7.user.sqlservice;

import java.util.HashMap;
import java.util.Map;

import vol1.jhcode.ch7.user.sqlservice.exception.SqlNotFoundException;
import vol1.jhcode.ch7.user.sqlservice.exception.SqlRetrievalFailureException;

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
