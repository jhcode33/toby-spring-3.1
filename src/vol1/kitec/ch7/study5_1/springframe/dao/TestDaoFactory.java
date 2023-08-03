package vol1.kitec.ch7.study5_1.springframe.dao;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import vol1.kitec.ch7.study5_1.springframe.sqlservice.OxmSqlService;
import vol1.kitec.ch7.study5_1.springframe.sqlservice.updatable.ConcurrentHashMapSqlRegistry;

@Configuration
public class TestDaoFactory {
	
	@Bean
	public DataSource dataSource() {
		
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		
		dataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class);
		dataSource.setUrl("jdbc:mysql://localhost:3306/testdb?characterEncoding=UTF-8");
		dataSource.setUsername("root");
		dataSource.setPassword("1234");

		return dataSource;
	}	

	@Bean
	public UserDaoJdbc userDao() {
		UserDaoJdbc userDaoJdbc = new UserDaoJdbc();
		userDaoJdbc.setDataSource(dataSource());
		userDaoJdbc.setSqlService(sqlService());
		return userDaoJdbc;
	}
	
	@Bean
	public OxmSqlService sqlService() {
		OxmSqlService oxmSqlService = new OxmSqlService();
		oxmSqlService.setUnmarshaller(unmarshaller());
//		Resource sqlmap = new ClassPathResource(
//				"/com/intheeast/springframe/dao/sqlmap.xml", 
//				UserDao.class);
//		oxmSqlService.setSqlmap(sqlmap);
		oxmSqlService.setSqlRegistry(sqlRegistry());
		return oxmSqlService;
	}
	
	@Bean
	public ConcurrentHashMapSqlRegistry sqlRegistry() {
		ConcurrentHashMapSqlRegistry concurrentHashMapSqlRegistry = new ConcurrentHashMapSqlRegistry();
		return concurrentHashMapSqlRegistry;
	}
	
	@Bean
	public Jaxb2Marshaller unmarshaller() {
		Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
		jaxb2Marshaller.setContextPath("com.intheeast.springframe.sqlservice.jaxb");
		return jaxb2Marshaller;
	}

}


