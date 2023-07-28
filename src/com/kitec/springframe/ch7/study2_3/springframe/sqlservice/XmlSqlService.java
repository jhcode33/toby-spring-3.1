package com.kitec.springframe.ch7.study2_3.springframe.sqlservice;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.kitec.springframe.ch7.study2_3.springframe.dao.UserDao;
import com.kitec.springframe.ch7.study2_3.springframe.sqlservice.jaxb.SqlType;
import com.kitec.springframe.ch7.study2_3.springframe.sqlservice.jaxb.Sqlmap;

public class XmlSqlService implements SqlService {

	private Map<String, String> sqlMap = new HashMap<String, String>();

	private String sqlmapFile;

	public void setSqlmapFile(String sqlmapFile) {
		this.sqlmapFile = sqlmapFile;
	}

	@PostConstruct
	public void loadSql() {
		String contextPath = Sqlmap.class.getPackage().getName(); 

		try {
			// xml을 디코딩할 때 필요한 클래스가 담겨져있는 패키지에 대한 정보를 context를 생성할 때 넘겨줌
			JAXBContext context = JAXBContext.newInstance(contextPath);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			
			// inputstream은 파일 포맷에 상관없이 바이트 단위로 해당 파일의 데이터를 읽는다
			InputStream is = UserDao.class.getResourceAsStream(this.sqlmapFile);
			Sqlmap sqlmap = (Sqlmap)unmarshaller.unmarshal(is);
			
			// xml 형태의 데이터가 클래스 객체로 사용하기 위해서 속성을 하나씩 Map 타입의 key, value로 바인딩
			for(SqlType sql : sqlmap.getSql()) {
				sqlMap.put(sql.getKey(), sql.getValue());
			}
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		} 
	}

	public String getSql(String key) throws SqlRetrievalFailureException {
		String sql = sqlMap.get(key);
		if (sql == null)  
			throw new SqlRetrievalFailureException(key + "를 이용해서 SQL을 찾을 수 없습니다");
		else
			return sql;
	}

}
