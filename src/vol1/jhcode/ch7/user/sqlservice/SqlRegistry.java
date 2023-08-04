package vol1.jhcode.ch7.user.sqlservice;

import vol1.jhcode.ch7.user.sqlservice.exception.SqlNotFoundException;

public interface SqlRegistry {
	
	void registerSql(String key, String sql);
	
	String findSql(String key) throws SqlNotFoundException;

}
