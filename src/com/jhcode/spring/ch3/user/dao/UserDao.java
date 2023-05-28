package com.jhcode.spring.ch3.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;

import com.jhcode.spring.ch3.user.domain.User;



public class UserDao {
	
	//== DataSource 사용하기 ==//
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public void jdbcContextWithStatementsStrategy(StatementStrategy stmt) throws SQLException{
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
	
	public void add(final User user) throws ClassNotFoundException, SQLException{
		class AddStatementLocalClass implements StatementStrategy {
			
			@Override
			public PreparedStatement makePreparedStatement(Connection con) throws SQLException {

				String sql = "INSERT INTO users(id, name, password) values(?,?,?)";
				PreparedStatement pst = con.prepareStatement(sql);
				pst.setString(1, user.getId());
				pst.setString(2, user.getName());
				pst.setString(3, user.getPassword());
				
				return pst;
			}
		}//내부 클래스의 끝
		
		StatementStrategy st = new AddStatementLocalClass();
		jdbcContextWithStatementsStrategy(st);
	
	}//외부 클래스의 끝
	
	public User get(String id) throws ClassNotFoundException, SQLException {
		Connection con = dataSource.getConnection();
		
		String sql = "SELECT * FROM users WHERE id=?";
		PreparedStatement pst = con.prepareStatement(sql);
		pst.setString(1, id);
		
		ResultSet rs = pst.executeQuery();
		User user = null;
		if (rs.next()) {
			user = new User();
			user.setId(rs.getString("id"));
			user.setName(rs.getString("name"));
			user.setPassword(rs.getString("password"));
		}
		
		if(user == null) throw new EmptyResultDataAccessException(1);
		
		rs.close();
		pst.close();
		con.close();
		
		return user;
		
	}
	
	//== 테이블 정보 삭제 ==//
	public void deleteAll() throws SQLException {
		StatementStrategy st = new DeleteAllStatement();
		jdbcContextWithStatementsStrategy(st);
	}
	
	//== 테이블 정보 개수 조회 ==//
	public int getCount() throws SQLException {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try {
			con = dataSource.getConnection();
			
			String sql = "SELECT COUNT(*) FROM users";
			pst = con.prepareStatement(sql);
			
			rs = pst.executeQuery();
			rs.next();
			return rs.getInt(1);
			
		} catch (Exception e) {
			throw e;
			
		} finally {
			if (rs  != null) { try {rs.close();  } catch (SQLException e) {} }
			if (pst != null) { try {pst.close(); } catch (SQLException e) {} }
			if (con != null) { try {con.close(); } catch (SQLException e) {} }
		}
	}
}
