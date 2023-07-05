package com.kitec.springframe.ch1.study3_3.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//== concrete product ==//
public class DConnectionMaker implements ConnectionMaker {

	@Override
	public Connection makeConnection() throws ClassNotFoundException, SQLException {
		Class.forName("org.mariadb.jdbc.Driver");
		Connection c = DriverManager.getConnection("jdbc:mariadb://localhost:3306/toby_kitec?characterEncoding=UTF-8", 
				"root",
				"1234");
		return c;
	}
}
