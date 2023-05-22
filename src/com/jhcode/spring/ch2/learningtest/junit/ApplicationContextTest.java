package com.jhcode.spring.ch2.learningtest.junit;

import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;

import com.jhcode.spring.ch2.user.dao.UserDao;

@ContextConfiguration
public class ApplicationContextTest {
	
	@Bean
	public UserDao userDao() {
		UserDao userDao = new UserDao();
		return userDao;
	}
}
