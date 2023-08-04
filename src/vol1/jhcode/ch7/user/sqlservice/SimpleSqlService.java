package vol1.jhcode.ch7.user.sqlservice;

import java.util.Map;

import vol1.jhcode.ch7.user.sqlservice.exception.SqlRetrievalFailureException;

public class SimpleSqlService implements SqlService {
	
	private Map<String, String> sqlMap;
	
	public void setSqlMap(Map<String, String> sqlMap) {
		this.sqlMap = sqlMap;
	}

	@Override
	public String getSql(String key) throws SqlRetrievalFailureException {
		String sql = sqlMap.get(key);
		
		if(sql == null) {
			throw new SqlRetrievalFailureException(key + "에 대한 SQL을 찾을 수 없습니다.");
		
		} else {
			return sql;
		}
	}
}
