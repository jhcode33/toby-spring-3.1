package vol1.jhcode.ch7.user.slqservice;

import vol1.jhcode.ch7.user.slqservice.exception.SqlNotFoundException;

public interface SqlRegistry {
	
	void registerSql(String key, String sql);
	
	String findSql(String key) throws SqlNotFoundException;

}
