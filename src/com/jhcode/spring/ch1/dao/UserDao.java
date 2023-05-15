package com.jhcode.spring.ch1.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jhcode.spring.ch1.domain.User;

public class UserDao {
	//이미 스프링 컨테이너가 관리하고 있기 때문에 사용 중에 변경되지 않는 읽기 전용 인스턴스이다.
	private ConnectionMaker connectionMaker;
	//스프링 IoC 컨테이너가 관리하지 않기 때문에 싱글톤으로 유지 되지 않는다. 매번 새로운 값으로 바뀌어 문제가 발생할 수 있다.
	private Connection con;
	private User user;
	
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
