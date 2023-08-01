package vol1.kitec.ch7.study3_3.springframe.sqlservice;

import java.util.Map;

public class SimpleSqlService implements SqlService {

	private Map<String, String> sqlMap;
	
	public void setSqlMap(Map<String, String> sqlMap) {
		this.sqlMap = sqlMap;
	}

	public String getSql(String key) throws SqlRetrievalFailureException {
		String sql = sqlMap.get(key);
		if (sql == null)  
			throw new SqlRetrievalFailureException(key + "를 이용해서 SQL을 찾을 수 없습니다");
		else
			return sql;
	}

}
