package com.jhcode.spring.ch3.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.jhcode.spring.ch3.user.domain.User;

public class AddStatement implements StatementStrategy  {
	
	User user;
	
	//== 생성자를 통해 필요한 객체 주입받기 ==//
	public AddStatement(User user) {
		this.user = user;
	}

	@Override
	public PreparedStatement makePreparedStatement(Connection con) throws SQLException {
		
		String sql = "INSERT INTO users(id, name, password) values(?,?,?)";
		PreparedStatement pst = con.prepareStatement(sql);
		pst.setString(1, user.getId());
		pst.setString(2, user.getName());
		pst.setString(3, user.getPassword());
		
		return pst;
	}
}
