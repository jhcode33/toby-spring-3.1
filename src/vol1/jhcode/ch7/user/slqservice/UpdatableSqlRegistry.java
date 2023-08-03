package vol1.jhcode.ch7.user.slqservice;

import java.util.Map;

import vol1.jhcode.ch7.user.slqservice.exception.SqlUpdateFailureException;

public interface UpdatableSqlRegistry extends SqlRegistry {
	
	public void updateSql(String key, String gsql) throws SqlUpdateFailureException;
	
	public void updateSql(Map<String, String> sqlmap) throws SqlUpdateFailureException; 
}
