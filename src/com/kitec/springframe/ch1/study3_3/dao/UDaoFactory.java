package com.kitec.springframe.ch1.study3_3.dao;

public class UDaoFactory implements DaoFactory {

	@Override
	public UserDao createUserDao() {
		
		UserDao userDao = new UserDao(new UConnectionMaker());
		
		return userDao;
	}

}
