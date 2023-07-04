package com.kitec.springframe.ch1.study3_3.dao;

public class DUserDao extends UserDao {

	@Override
	void createProduct() {
		this.connectionMaker =  new DConnectionMaker();
	}
}
