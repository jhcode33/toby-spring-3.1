package com.kitec.springframe.ch2.study3_4.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.kitec.springframe.ch2.study3_4.domain.User;

public class UserDaoTest {	
	
	@Test
	public void addAndGet() throws SQLException, ClassNotFoundException {				
		ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
		UserDao dao = context.getBean("userDao", UserDao.class);
		
		User user1 = new User("gyumee", "gyumee", "springno1");
		User user2 = new User("leegw700", "leegw700", "springno2");
		
		dao.deleteAll();		
		assertEquals(dao.getCount(), 0);
		
		dao.add(user1);
		dao.add(user2);
		assertEquals(dao.getCount(), 2);
		
		User userget1 = dao.get(user1.getId());
		assertEquals(user1.getName(), userget1.getName());
		assertEquals(user1.getPassword(), userget1.getPassword());
		
		User userget2 = dao.get(user2.getId());
		assertEquals(user2.getName(), userget2.getName());
		assertEquals(user2.getPassword(), userget2.getPassword());		
	}   
	
	@Test
	public void count() throws SQLException, ClassNotFoundException {
		ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
		UserDao dao = context.getBean("userDao", UserDao.class);
		User user1 = new User("gyumee", "gyumee", "springno1");
		User user2 = new User("leegw700", "leegw700", "springno2");
		User user3 = new User("bumjin", "bumjin", "springno3");
		
		dao.deleteAll();
		assertEquals(dao.getCount(), 0);

		dao.add(user1);
		assertEquals(dao.getCount(), 1);
		
		dao.add(user2);
		assertEquals(dao.getCount(), 2);
		
		dao.add(user3);
		assertEquals(dao.getCount(), 3);	
		
	}

}