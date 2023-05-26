package com.jhcode.spring.ch3.user.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteAllStatement implements StatementStrategy {

	//== deleteAll() 메소드에서 사용될 구체적인 PreparedStatement 객체 생성 구현부 ==//
	@Override
	public PreparedStatement makePreparedStatement(Connection con) throws SQLException {
		
		String sql = "DELETE FROM users";
		PreparedStatement pst = con.prepareStatement(sql);
		return pst;
	}
}
