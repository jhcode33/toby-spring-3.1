package com.jhcode.spring.ch1.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DaoFactory {
	
	@Bean
	public UserDao userDao() {
		ConnectionMaker connectionMaker = new DConnectionMaker();
		UserDao userDao = new UserDao();
		userDao.setConnectionMaker(connectionMaker);
//<property name="connectionMaker" ref="connectionMaker" />
		return userDao;
	}
	
	@Bean															//==> <bean
	public ConnectionMaker connectionMaker() {						//==> id="connectionMaker"
		ConnectionMaker connectionMaker = new DConnectionMaker();	//==> com.jhcode.spring.ch1.dao.DConnectionMaker
		return connectionMaker;
	}
}
