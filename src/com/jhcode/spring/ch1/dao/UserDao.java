package com.jhcode.spring.ch1.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jhcode.spring.ch1.domain.User;

public class UserDao {
	
	
	//생성자 : 이전 코드
	//public UserDao(ConnectionMaker connectionMaker) {
	//	this.connectionMaker = connectionMaker;
	//}
	
	//== 싱글톤 ==//
	private static UserDao INSTANCE;
	
	private ConnectionMaker connectionMaker;
	
	private UserDao(ConnectionMaker connectionMaker) {
		this.connectionMaker = connectionMaker;
	}
	
	public static synchronized UserDao getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new UserDao(connectionMaker);
			return INSTANCE;
		}
		return INSTANCE;
	}
	
	
	
	
	
	
	public void add(User user) throws ClassNotFoundException, SQLException{
		Connection con = connectionMaker.makeConnection();
		
		//프리페어 스테이트먼츠 사용
		String sql = "INSERT INTO users(id, name, password) values(?,?,?)";
		PreparedStatement pst = con.prepareStatement(sql);
		pst.setString(1, user.getId());
		pst.setString(2, user.getName());
		pst.setString(3, user.getPassword());
		
		pst.executeUpdate();
		
		pst.close();
		con.close();
		
	}
	
	public User get(String id) throws ClassNotFoundException, SQLException {
		Connection con = connectionMaker.makeConnection();
		
		String sql = "SELECT * FROM users WHERE id=?";
		PreparedStatement pst = con.prepareStatement(sql);
		pst.setString(1, id);
		
		ResultSet rs = pst.executeQuery();
		User user = new User();
		if (rs.next()) {
			user.setId(rs.getString("id"));
			user.setName(rs.getString("name"));
			user.setPassword(rs.getString("password"));
		}
		rs.close();
		pst.close();
		con.close();
		
		return user;
		
	}
}