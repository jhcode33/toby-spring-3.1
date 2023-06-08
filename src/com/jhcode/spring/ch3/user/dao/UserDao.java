package com.jhcode.spring.ch3.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import com.jhcode.spring.ch3.user.domain.User;

public class UserDao {
	
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public void add(final User user) throws ClassNotFoundException, SQLException{
		String sql = "INSERT INTO users(id, name, password) values(?,?,?)"; 
		this.jdbcTemplate.update(sql, user.getId(), user.getName(), user.getPassword());
	}
	
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
	
	public void deleteAll() throws SQLException {
		String sql = "DELETE FROM users";
		//콜백 객체 생성을 내장 함수가 담당한다.
		this.jdbcTemplate.update(sql);
	}
	
	//== 테이블 정보 개수 조회 ==//
	public int getCount() throws SQLException {
		String sql = "SELECT COUNT(*) FROM users";
		
		List<Integer> result = jdbcTemplate.query(sql,
				(rs, rowNum) -> rs.getInt(1));
		
		return (int)DataAccessUtils.singleResult(result);
	}
}
