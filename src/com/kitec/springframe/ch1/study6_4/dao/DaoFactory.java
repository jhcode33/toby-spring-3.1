package com.kitec.springframe.ch1.study6_4.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DaoFactory {
	
	@Bean
	public UserDao userDao() {
		UserDao userDao = new UserDao();
		userDao.setConnectionMaker(connectionMaker());
		return userDao;
	}	
	
	@Bean
	public ConnectionMaker connectionMaker() {
		DConnectionMaker maker = new DConnectionMaker();
		
		String driverClass = "org.mariadb.jdbc.Driver";
		String url = "jdbc:mariadb://localhost:3306/toby_kitec?characterEncoding=UTF-8";
		String username = "root";
		String password = "1234";
		
		maker.setDriverClass(driverClass);
		maker.setUrl(url);
		maker.setUsername(username);
		maker.setPassword(password);
		return maker;
	}

}
