package com.jhcode.spring.ch3.user.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.jhcode.spring.ch3.user.domain.User;

public class UserDao {
	
	private JdbcTemplate jdbcTemplate;
	
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	//==RowMapper 객체를 생성하기 위한 메소드==//
	private RowMapper<User> userRowMapper() {
        return ((rs, rowNum) -> {
        	User user = new User();
        	user.setId(rs.getString("id"));
        	user.setName(rs.getString("name"));
        	user.setPassword(rs.getString("password"));            
            return user;
        });
    }
		
	//== DB에 user 추가 ==//
	public void add(final User user) throws ClassNotFoundException, SQLException{
		String sql = "INSERT INTO users(id, name, password) values(?,?,?)"; 
		this.jdbcTemplate.update(sql, user.getId(), user.getName(), user.getPassword());
	}
	
	//== DB에서 id에 해당하는 user 정보 검색 ==//
	public Optional<User> get(String id) throws SQLException {
		String sql = "SELECT * FROM users WHERE id = ?";	    		
	    
	    try (Stream<User> stream = jdbcTemplate.queryForStream(sql, userRowMapper(), id)) {
	        return stream.findFirst();
	    } catch (DataAccessException e) {
	        return Optional.empty();
	    }
	}

	//== 테이블 전체 데이터 삭제 ==//
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
	
	//== 테이블에 있는 전체 User 정보 가져오기
	public List<User> getAll() throws DataAccessException, SQLException {
		String sql = "SELECT * FROM users ORDER BY id DESC";
		
		return this.jdbcTemplate.query(sql, userRowMapper());
	}
}
