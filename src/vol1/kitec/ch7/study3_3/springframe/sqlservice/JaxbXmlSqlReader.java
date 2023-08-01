package vol1.kitec.ch7.study3_3.springframe.sqlservice;

import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import vol1.kitec.ch7.study3_3.springframe.dao.UserDao;
import vol1.kitec.ch7.study3_3.springframe.sqlservice.jaxb.SqlType;
import vol1.kitec.ch7.study3_3.springframe.sqlservice.jaxb.Sqlmap;

public class JaxbXmlSqlReader implements SqlReader {

	private final String DEFAULT_SQLMAP_FILE = "sqlmap.xml";
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
