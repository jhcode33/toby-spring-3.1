package com.jhcode.spring.ch3.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDaoDeleteAll extends UserDao {
	
	protected PreparedStatement makeStatement(Connection con) throws SQLException {
		String sql = "DELETE FROM users";
		PreparedStatement pst = con.prepareStatement(sql);
		return pst;
		
	}
}
