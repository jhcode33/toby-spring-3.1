package vol1.kitec.ch7.study5_3.springframe.sqlservice;

public class DefaultSqlService extends BaseSqlService {
	public DefaultSqlService() {
		setSqlReader(new JaxbXmlSqlReader());
		setSqlRegistry(new HashMapSqlRegistry());
	}
}
