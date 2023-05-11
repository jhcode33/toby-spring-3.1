package com.jhcode.spring.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SimpleConnectionMaker {
	
	//== Connection 객체를 생성하여 반환하는 메서드 ==//
	public Connection makeNewConnection() throws ClassNotFoundException, SQLException{
		String className = "org.mariadb.jdbc.Driver";
		String url = "jdbc:mariadb://localhost:3306/toby_study?characterEncoding=UTF-8";
		String userId = "root";
		String password = "1234";
		
		Class.forName(className);
		Connection con = DriverManager.getConnection(url, userId, password);
		
		return con;
	}
}
