package vol1.jhcode.ch7.user.sqlservice;

import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import vol1.jhcode.ch7.user.dao.UserDao;
import vol1.jhcode.ch7.user.sqlservice.jaxb.SqlType;
import vol1.jhcode.ch7.user.sqlservice.jaxb.Sqlmap;

public class JaxbXmlSqlReader implements SqlReader {
	//== SqlReader 구현부를 분리 ==//
	
	private static final String DEFAULT_SQLMAP_FILE = "sqlmap.xml";
	private String sqlmapFile = DEFAULT_SQLMAP_FILE;
	
	public void setSqlmapFile(String sqlmapFile) {
		this.sqlmapFile =sqlmapFile;
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
}
