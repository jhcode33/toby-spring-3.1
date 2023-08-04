package vol1.jhcode.ch7.user.sqlservice;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import vol1.jhcode.ch7.user.dao.UserDao;
import vol1.jhcode.ch7.user.sqlservice.exception.SqlNotFoundException;
import vol1.jhcode.ch7.user.sqlservice.exception.SqlRetrievalFailureException;
import vol1.jhcode.ch7.user.sqlservice.jaxb.SqlType;
import vol1.jhcode.ch7.user.sqlservice.jaxb.Sqlmap;

public class XmlSqlService implements SqlService, SqlRegistry, SqlReader {
	//== SqlService 구현부 ==//
	private SqlReader sqlReader;
	private SqlRegistry sqlRegistry;
	
	public void setSqlReader(SqlReader sqlReader) {
		this.sqlReader = sqlReader;
	}
	
	public void setSqlRegistry(SqlRegistry sqlRegistry) {
		this.sqlRegistry = sqlRegistry;
	}
	
	
	@Override
	public String getSql(String key) throws SqlRetrievalFailureException {
		
		try {
			return this.sqlRegistry.findSql(key);
		} catch (SqlNotFoundException e) {
			throw new SqlRetrievalFailureException(e);
		}
	}
	
	//== SqlRegistry 구현부 ==//
	private Map<String, String> sqlMap = new HashMap<String, String>();
	
	@Override
	public String findSql(String key) throws SqlNotFoundException {
		String sql = sqlMap.get(key);
		
		if (sql == null) 
			throw new SqlNotFoundException(key + "에 대한 SQL을 찾을 수 없습니다.");
		else return sql;
	}
	
	@Override
	public void registerSql(String key, String sql) {
		sqlMap.put(key, sql);
	}

	//== SqlReader 구현부 ==//
	private String sqlmapFile;
	
	public void setSqlmapFile(String sqlmapFile) {
		this.sqlmapFile = sqlmapFile;
	}
	
	@Override
	public void read(SqlRegistry sqlRegistry) {
		String contextPath = Sqlmap.class.getPackage().getName();

		try {
			JAXBContext context = JAXBContext.newInstance(contextPath);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			InputStream is = UserDao.class.getResourceAsStream(this.sqlmapFile);
			Sqlmap sqlmap = (Sqlmap)unmarshaller.unmarshal(is);
			
			for (SqlType sql : sqlmap.getSql()) {
				sqlRegistry.registerSql(sql.getKey(), sql.getValue());
			}
			
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}
	
	//== XmlSqlService 구현 방법에 따른 초기화 메소드 ==//
	@PostConstruct
	public void loadSql() {
		this.sqlReader.read(this.sqlRegistry);
	}
	
}
