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
	
	//== 템플릿 메소드 ==//
	//인터페이스 타입으로 익명 내부 클래스를 구현한 객체를 매개변수로 받는다.
	public void workWithStatementStrategy(StatementStrategy stmt) throws SQLException {
		Connection con = null;
		PreparedStatement pst = null;
		
		try {
			//참조 정보 생성
			con = dataSource.getConnection();
			
			//콜백 메소드 호출하고 파라미터로 참조 정보 전달, 결과값을 pst에 대입
			pst = stmt.makePreparedStatement(con);
			
			pst.executeUpdate();
			
		} catch (SQLException e) {
			throw e;
			
		} finally {
			if (pst != null) { try {pst.close(); } catch (SQLException e) {} }
			if (con != null) { try {con.close(); } catch (SQLException e) {} }
		}
	}
	
	public void executeSql(final String query) throws SQLException {
		
		workWithStatementStrategy(new StatementStrategy() {
			
			@Override
			public PreparedStatement makePreparedStatement(Connection con) throws SQLException {
				return con.prepareStatement(query);
			}
		});
	}
}
