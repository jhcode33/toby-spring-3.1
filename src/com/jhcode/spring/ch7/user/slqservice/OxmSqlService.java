package com.jhcode.spring.ch7.user.slqservice;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.springframework.oxm.Unmarshaller;

import com.jhcode.spring.ch7.user.dao.UserDao;
import com.jhcode.spring.ch7.user.slqservice.exception.SqlRetrievalFailureException;
import com.jhcode.spring.ch7.user.slqservice.jaxb.SqlType;
import com.jhcode.spring.ch7.user.slqservice.jaxb.Sqlmap;

public class OxmSqlService implements SqlService {
	// final로 변경 불가능하며, 두 개의 클래스는 강하게 결합되어 있다
	private final OxmSqlReader oxmSqlReader = new OxmSqlReader();
	
	// 디폴트 오브젝트로 만들어진 프로퍼티, 필요에 따라 setter으로 변경한다
	private SqlRegistry sqlRegistry = new HashMapSqlRegistry();
	
	public void setSqlRegistry(SqlRegistry sqlRegistry) {
		this.sqlRegistry = sqlRegistry;
	}
	
	//== OxmSqlService를 통해서 간접적으로 OxmSqlReader에게 DI한다 ==//
	public void setUnmarshaller(Unmarshaller unmarshaller) {
		this.oxmSqlReader.setUnmarshaller(unmarshaller);
	}
	
	public void setSqlmapFile(String sqlmapFile) {
		this.oxmSqlReader.setSqlmapFile(sqlmapFile);
	}
	
	//== SqlService의 구현 코드 ==//
	@PostConstruct
	public void loadSql() {
		this.oxmSqlReader.read(this.sqlRegistry);
	}
	
	@Override
	public String getSql(String key) throws SqlRetrievalFailureException {
		try {
			return this.sqlRegistry.findSql(key);
		} catch (Exception e) {
			throw new SqlRetrievalFailureException(e);
		}
	}
	
	//== 내부 클래스 ==//
	private class OxmSqlReader implements SqlReader{
		private Unmarshaller unmarshaller;
		private final static String DEFAULT_SQLMAP_FILE = "sqlmap.xml";
		private String sqlmapFile = DEFAULT_SQLMAP_FILE;
		
		public void setUnmarshaller(Unmarshaller unmarshaller) {
			this.unmarshaller = unmarshaller;
		}
		
		public void setSqlmapFile(String sqlmapFile) {
			this.sqlmapFile = sqlmapFile;
		}

		@Override
		public void read(SqlRegistry sqlRegistry) {
			try {
				Source source = new StreamSource(
						UserDao.class.getResourceAsStream(this.sqlmapFile));
				Sqlmap sqlmap = (Sqlmap) this.unmarshaller.unmarshal(source);
				
				for(SqlType sql : sqlmap.getSql()) {
					sqlRegistry.registerSql(sql.getKey(), sql.getValue());
				}
			} catch (IOException e) {
				throw new IllegalArgumentException(this.sqlmapFile + "을 가져올 수 없습니다.");
			}
		}
		
	}

}
