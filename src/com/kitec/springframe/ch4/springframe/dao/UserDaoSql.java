package com.kitec.springframe.ch4.springframe.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;

import com.kitec.springframe.ch4.springframe.domain.User;

public class UserDaoSql {	
	
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	private RowMapper<User> userMapper = 
		new RowMapper<User>() {
				public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User user = new User();
				user.setId(rs.getString("id"));
				user.setName(rs.getString("name"));
				user.setPassword(rs.getString("password"));
				return user;
			}
		};
		
	private RowMapper<User> userMapper() {
			
		//메소드 이기 때문에 return이 있어야함
		//람다식으로 구현한 RowMapper 함수형 인터페이스의 mapRow(ResultSet rs, int rowNum)을 구현해서 반환함.
			return ((rs, rowNum) ->{
				User user = new User();
				user.setId(rs.getString("id"));
				user.setName(rs.getString("name"));
				user.setPassword(rs.getString("password"));
				return user;
			});
	}	

	public void add(User user) throws SQLException {
		Connection c = dataSource.getConnection();
		
		String sql = "insert into users(id, name, password) values(?,?,?)";
				
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, user.getId());
		ps.setString(2, user.getName());
		ps.setString(3, user.getPassword());
		
		ps.executeUpdate();
	}

	public Optional<User> get(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<User> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

}
