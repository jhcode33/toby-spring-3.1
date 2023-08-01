package vol1.jhcode.ch7.user.slqservice;

import vol1.jhcode.ch7.user.slqservice.exception.SqlRetrievalFailureException;

public interface SqlService {
	String getSql(String key) throws SqlRetrievalFailureException;
}
