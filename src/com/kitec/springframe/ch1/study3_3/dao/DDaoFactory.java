package com.kitec.springframe.ch1.study3_3.dao;

//== Concrete Creator ==//
public class DDaoFactory implements DaoFactory {
	
	@Override
	public UserDao createUserDao() {
		
		UserDao	userDao = new UserDao(new DConnectionMaker());
		
		return userDao;
	}
}
