package com.kitec.springframe.ch1.study3_3.dao;

public class UUserDao extends UserDao {

	@Override
	void createProduct() {
		this.connectionMaker = new UConnectionMaker();
	}

}
