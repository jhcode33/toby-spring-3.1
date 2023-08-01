package vol1.jhcode.ch7.user.slqservice;

import javax.annotation.PostConstruct;

import vol1.jhcode.ch7.user.slqservice.exception.SqlRetrievalFailureException;

public class BaseSqlService implements SqlService{
	// XmlSqlService에서 SqlService 구현부를 분리
	private SqlReader sqlReader;
	private SqlRegistry sqlRegistry;
	
	public void setSqlReader(SqlReader sqlReader) {
		this.sqlReader = sqlReader;
	}
	
	public void setSqlRegistry(SqlRegistry sqlRegistry) {
		this.sqlRegistry = sqlRegistry;
	}
	
	@PostConstruct
	public void loadSql() {
		this.sqlReader.read(this.sqlRegistry);
	}
	
	@Override
	public String getSql(String key) throws SqlRetrievalFailureException {
		try {
			return this.sqlRegistry.findSql(key);
		} catch (Exception e) {
			throw new SqlRetrievalFailureException(e);
		}
	}
}
