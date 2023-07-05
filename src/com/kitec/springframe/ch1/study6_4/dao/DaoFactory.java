package com.kitec.springframe.ch1.study6_4.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DaoFactory {
	
	@Bean
	public UserDao userDao() {
		return new UserDao(connectionMaker());
	}	
	
	@Bean
	public ConnectionMaker connectionMaker() {
		return new DConnectionMaker();
	}

}
