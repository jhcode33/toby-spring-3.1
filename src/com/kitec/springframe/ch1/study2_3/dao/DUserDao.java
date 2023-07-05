package com.kitec.springframe.ch1.study2_3.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DUserDao  extends UserDao {

	//MariaDB를 사용하는 DUserDao
	@Override
	protected Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("org.mariadb.jdbc.Driver");
		Connection c = DriverManager.getConnection("jdbc:mariadb://localhost:3306/toby_kitec?characterEncoding=UTF-8", 
				"root",
				"1234");
		
		return c;
	}

}
