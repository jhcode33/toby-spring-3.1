package vol1.jhcode.ch7.user.sqlservice;

import java.util.Map;

import vol1.jhcode.ch7.user.sqlservice.exception.SqlUpdateFailureException;

public interface UpdatableSqlRegistry extends SqlRegistry {
	
	public void updateSql(String key, String sql) throws SqlUpdateFailureException;
	
	public void updateSql(Map<String, String> sqlmap) throws SqlUpdateFailureException; 
}
