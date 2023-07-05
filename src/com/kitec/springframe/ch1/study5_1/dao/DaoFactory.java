package com.kitec.springframe.ch1.study5_1.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DaoFactory {
	
	@Bean
	public UserDao userDao() {
		return new UserDao(dConnectionMaker());
	}	
	
	@Bean
	public ConnectionMaker dConnectionMaker() {
		return new DConnectionMaker();
	}
	
	@Bean
	public ConnectionMaker nConnectionMaker() {
		return new NConnectionMaker();
	}

}
