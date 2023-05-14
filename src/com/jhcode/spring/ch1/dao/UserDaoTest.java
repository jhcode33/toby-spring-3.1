package com.jhcode.spring.ch1.dao;

import java.sql.SQLException;

import com.jhcode.spring.ch1.domain.User;

public class UserDaoTest {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		//==> DaoFactory로 코드 이동
		//ConnectionMaker connectionMaker = new DConnectionMaker();
		//UserDao dao = new UserDao(connectionMaker);

		//Factory에서 결정하고 생성한 Dao 객체를 반환받아 사용함.
		UserDao dao = new DaoFactory().userDao();
		
		User user = new User();
		user.setId("whiteship");
		user.setName("jhcode");
		user.setPassword("mariaDB");

		dao.add(user);
			
		System.out.println(user.getId() + " 등록 성공");
		
		User user2 = dao.get(user.getId());
		System.out.println(user2.getName());
		System.out.println(user2.getPassword());
			
		System.out.println(user2.getId() + " 조회 성공");
	}
}
