package com.kitec.springframe.ch1.study3_3.dao;

import java.sql.SQLException;

import com.kitec.springframe.ch1.study3_3.domain.User;

public class UserDaoTest {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
//		ConnectionMaker connectionMaker = new DConnectionMaker();
//		UserDao dao = new UserDao(connectionMaker);

		ConnectionFactory connectionFactory = new ConnectionFactory();
		ConnectionMaker connectionMaker = connectionFactory.createConnectionMaker("D");
		
		//== UserDao는 어떤 ConnectionMaker의 구현체가 생성되는지 모르고, Client가 결정하고 생성한 ConnectionMaker 구현체를
		//== Interface 타입으로 받아서 사용한다.
		UserDao dao = new UserDao(connectionMaker);
		
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
