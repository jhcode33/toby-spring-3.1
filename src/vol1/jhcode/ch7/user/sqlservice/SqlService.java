package vol1.jhcode.ch7.user.sqlservice;

import vol1.jhcode.ch7.user.sqlservice.exception.SqlRetrievalFailureException;

public interface SqlService {
	String getSql(String key) throws SqlRetrievalFailureException;
}
