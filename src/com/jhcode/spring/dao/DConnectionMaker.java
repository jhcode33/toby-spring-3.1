package com.jhcode.spring.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DConnectionMaker implements ConnectionMaker {

	@Override
	public Connection makeConnection() throws ClassNotFoundException, SQLException {
		//D사의 독자적인 방법으로 Connection을 생성하는 코드
		String className = "org.mariadb.jdbc.Driver";
		String url = "jdbc:mariadb://localhost:3306/toby_study?characterEncoding=UTF-8";
		String userId = "root";
		String password = "1234";
		
		Class.forName(className);
		Connection dCon = DriverManager.getConnection(url, userId, password);
		
		return dCon;
	}

}
