package com.jhcode.spring.ch1.dao;
//package com.jhcode.spring.dao;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//public class DUserDao extends UserDao {
//
//	@Override
//	public Connection getConnection() throws ClassNotFoundException, SQLException {
//		//== D사 DB Connection 생성 코드 ==//
//		String className = "org.mariadb.jdbc.Driver";
//		String url = "jdbc:mariadb://localhost:3306/toby_study?characterEncoding=UTF-8";
//		String userId = "root";
//		String password = "1234";
//		
//		Class.forName(className);
//		Connection con = DriverManager.getConnection(url, userId, password);
//		
//		return con;
//	}
//
//}