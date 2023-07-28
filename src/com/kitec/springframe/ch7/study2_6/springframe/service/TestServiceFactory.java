package com.kitec.springframe.ch7.study2_6.springframe.service;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.kitec.springframe.ch7.study2_6.springframe.dao.UserDaoJdbc;
import com.kitec.springframe.ch7.study2_6.springframe.sqlservice.BaseSqlService;
import com.kitec.springframe.ch7.study2_6.springframe.sqlservice.HashMapSqlRegistry;
import com.kitec.springframe.ch7.study2_6.springframe.sqlservice.JaxbXmlSqlReader;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "springframe")
public class TestServiceFactory {	
	
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
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }
	
	// 자기 참조 빈 설정 : XmlSqlService의 SqlRegistry, SqlReader 인터페이스 구현 정의의 주석처리 제거
//	@Bean 
//	public XmlSqlService sqlService() {
//		XmlSqlService xmlSqlService = new XmlSqlService();
//		xmlSqlService.setSqlReader(xmlSqlService);
//		xmlSqlService.setSqlRegistry(xmlSqlService);
//		xmlSqlService.setSqlmapFile("sqlmap.xml");
//      return xmlSqlService;
//	}
	
	@Bean
    public BaseSqlService sqlService() {
		BaseSqlService baseSqlService = new BaseSqlService();
		baseSqlService.setSqlReader(sqlReader());
		baseSqlService.setSqlRegistry(sqlRegistry());
        return baseSqlService;
    }
	
	@Bean
	public JaxbXmlSqlReader sqlReader() {
		JaxbXmlSqlReader jaxbXmlSqlReader = new JaxbXmlSqlReader();
		jaxbXmlSqlReader.setSqlmapFile("sqlmap.xml");
		return jaxbXmlSqlReader;
	}
	
	@Bean
	public HashMapSqlRegistry sqlRegistry() {
		HashMapSqlRegistry hashMapSqlRegistry = new HashMapSqlRegistry();
		return hashMapSqlRegistry;
	}

	// application components
	@Bean
	public UserDaoJdbc userDao() {
		UserDaoJdbc userDaoJdbc = new UserDaoJdbc();
		userDaoJdbc.setDataSource(dataSource());
		userDaoJdbc.setSqlService(sqlService());
		return userDaoJdbc;
	}	
	
	@Bean
	public UserServiceImpl userService() {
		UserServiceImpl userServiceImpl = new UserServiceImpl();
		userServiceImpl.setUserDao(userDao());
		userServiceImpl.setMailSender(mailSender());		
		return userServiceImpl;
	}	
	
	@Bean
	public UserServiceImpl testUserService() {
	    UserServiceImpl testUserServiceImpl = new UserServiceTest.TestUserServiceImpl();
	    testUserServiceImpl.setUserDao(userDao());
	    testUserServiceImpl.setMailSender(mailSender());
	    return testUserServiceImpl;
	}
	
	@Bean
	public DummyMailSender mailSender() {
		DummyMailSender dummyMailSender = new DummyMailSender();
		return dummyMailSender;
	}	
}
