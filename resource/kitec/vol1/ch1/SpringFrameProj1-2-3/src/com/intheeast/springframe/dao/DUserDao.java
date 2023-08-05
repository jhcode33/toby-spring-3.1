package com.intheeast.springframe.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Concrete Creator
public class DUserDao extends UserDao {

	@Override
	protected Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/sbdt_db1?characterEncoding=UTF-8", 
				"root",
				"1234");
		
		return c;
	}

}
