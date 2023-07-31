package com.kitec.springframe.ch7.study3_1.springframe.sqlservice;

import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.kitec.springframe.ch7.study3_1.springframe.dao.UserDao;
import com.kitec.springframe.ch7.study3_1.springframe.sqlservice.jaxb.SqlType;
import com.kitec.springframe.ch7.study3_1.springframe.sqlservice.jaxb.Sqlmap;

public class JaxbXmlSqlReader implements SqlReader {

	private static final String DEFAULT_SQLMAP_FILE = "sqlmap.xml";
	private String sqlmapFile = DEFAULT_SQLMAP_FILE;

	public void setSqlmapFile(String sqlmapFile) { this.sqlmapFile = sqlmapFile; }

	public void read(SqlRegistry sqlRegistry) {
		String contextPath = Sqlmap.class.getPackage().getName(); 
		try {
			JAXBContext context = JAXBContext.newInstance(contextPath);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			InputStream is = UserDao.class.getResourceAsStream(sqlmapFile);
			Sqlmap sqlmap = (Sqlmap)unmarshaller.unmarshal(is);
			for(SqlType sql : sqlmap.getSql()) {
				sqlRegistry.registerSql(sql.getKey(), sql.getValue());
			}
		} catch (JAXBException e) { throw new RuntimeException(e); } 		
	}

}