package com.kitec.springframe.ch1.study3_3.dao;

import java.sql.SQLException;

import com.kitec.springframe.ch1.study3_3.domain.User;

public class UserDaoTest {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
//		ConnectionMaker connectionMaker = new DConnectionMaker();
//		UserDao dao = new UserDao(connectionMaker);
		
		DaoFactory daoFactory = new DDaoFactory();
	  //DaoFactory daoFactory = new UDaoFactory();
		
		UserDao dao = daoFactory.createUserDao();
		
		User user = new User();
		user.setId("whiteship");
		user.setName("백기선");
		user.setPassword("married");

		dao.add(user);
			
		System.out.println(user.getId() + "등록 성공");
		
		User user2 = dao.get(user.getId());
		System.out.println(user2.getName());
		System.out.println(user2.getPassword());
			
		System.out.println(user2.getId() + "조회 성공");
	}

}
