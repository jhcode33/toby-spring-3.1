package vol1.kitec.ch7.study5_1.springframe.sqlservice;

public interface SqlService {
	String getSql(String key) throws SqlRetrievalFailureException;
}
