package com.jhcode.spring.ch7.user.slqservice;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.jhcode.spring.ch7.user.dao.UserDao;
import com.jhcode.spring.ch7.user.slqservice.jaxb.SqlType;
import com.jhcode.spring.ch7.user.slqservice.jaxb.Sqlmap;

public class XmlSqlService implements SqlService {
	// 읽어온 SQL을 저장해 둘 객체
	private Map<String, String> sqlMap = new HashMap<String, String>();
	
	private String sqlmapFile;
	
	public void setSqlmapFile(String sqlmapFile) {
		this.sqlmapFile = sqlmapFile;
	}
	
	// 생성자 대신 초기화에 사용할 메소드
	@PostConstruct
	public void loadSql() {
		String contextPath = Sqlmap.class.getPackage().getName();

		try {
			JAXBContext context = JAXBContext.newInstance(contextPath);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			InputStream is = UserDao.class.getResourceAsStream(this.sqlmapFile);
			Sqlmap sqlmap = (Sqlmap)unmarshaller.unmarshal(is);
			
			for (SqlType sql : sqlmap.getSql()) {
				sqlMap.put(sql.getKey(), sql.getValue());
			}
			
		} catch (JAXBException e) {
			// JAXB는 복구 불가능한 예외로 Reuntime 예외로 포장해서 던진다
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public String getSql(String key) throws SqlRetrievalFailureException {
		String sql = sqlMap.get(key);
		if (sql == null)
			throw new SqlRetrievalFailureException(key + "를 찾을 수 없습니다.");
		else 
			return sql;
	}
}
