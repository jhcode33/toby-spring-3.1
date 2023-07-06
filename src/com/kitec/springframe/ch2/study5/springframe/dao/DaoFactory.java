package com.kitec.springframe.ch2.study5.springframe.dao;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

@Configuration
public class DaoFactory {
	
	@Bean
	public DataSource dataSource() {
		
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		
		dataSource.setDriverClass(org.mariadb.jdbc.Driver.class);
		dataSource.setUrl("jdbc:mariadb://localhost:3306/toby_kitec?characterEncoding=UTF-8");
		dataSource.setUsername("root");
		dataSource.setPassword("1234");
		
//		dataSource.setDriverClass(org.mariadb.jdbc.Driver.class);
//		dataSource.setUrl("jdbc:mariadb://192.168.0.56:3306/toby_kitec?characterEncoding=UTF-8");
//		dataSource.setUsername("test1");
//		dataSource.setPassword("1234");
		
//		dataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class);
//		dataSource.setUrl("jdbc:mysql://192.168.0.76:3306/sbdt_db?characterEncoding=UTF-8");
//		dataSource.setUsername("root");
//		dataSource.setPassword("1234");
		
		return dataSource;
		
	}

	@Bean
	public UserDao userDao() {
		UserDao userDao = new UserDao();
		userDao.setDataSource(dataSource());
		return userDao;
	}

}
