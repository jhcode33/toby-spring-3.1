package com.jhcode.spring.ch3.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

public class JdbcContext {
	
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public void workWithStatementStrategy(StatementStrategy stmt) throws SQLException {
		Connection con = null;
		PreparedStatement pst = null;
		
		try {
			con = dataSource.getConnection();
			
			pst = stmt.makePreparedStatement(con);
			
			pst.executeUpdate();
			
		} catch (SQLException e) {
			throw e;
			
		} finally {
			if (pst != null) { try {pst.close(); } catch (SQLException e) {} }
			if (con != null) { try {con.close(); } catch (SQLException e) {} }
		}
	}
}
