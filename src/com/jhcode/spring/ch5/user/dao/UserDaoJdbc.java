package com.jhcode.spring.ch5.user.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.jhcode.spring.ch5.user.domain.Level;
import com.jhcode.spring.ch5.user.domain.User;

public class UserDaoJdbc implements UserDao {
	
	private JdbcTemplate jdbcTemplate;
	
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	//==RowMapper 객체를 생성하기 위한 익명 클래스 사용==//
	private RowMapper<User> userMapper = 
			new RowMapper<User>() {
					public User mapRow(ResultSet rs, int rowNum) throws SQLException {
					User user = new User();
					user.setId(rs.getString("id"));
					user.setName(rs.getString("name"));
					user.setPassword(rs.getString("password"));
					user.setLevel(Level.valueOf(rs.getInt("level")));
					user.setLogin(rs.getInt("login"));
					user.setRecommend(rs.getInt("recommend"));
					return user;
				}
			};
			
	//==RowMapper 객체를 생성하기 위한 메소드==//
	private RowMapper<User> userRowMapper() {
        return ((rs, rowNum) -> {
        	User user = new User();
			user.setId(rs.getString("id"));
			user.setName(rs.getString("name"));
			user.setPassword(rs.getString("password"));
			user.setLevel(Level.valueOf(rs.getInt("level")));
			user.setLogin(rs.getInt("login"));
			user.setRecommend(rs.getInt("recommend"));            
            return user;
        });
    }
		
	//== DB에 user 추가 ==//
	public void add(final User user) {
		String sql = "INSERT INTO users(id, name, password, level, login, recommend) " +
					 "VALUES(?,?,?,?,?,?)"; 
		this.jdbcTemplate.update(sql, user.getId(), 
									  user.getName(), 
									  user.getPassword(), 
									  user.getLevel().intValue(),
									  user.getLogin(), 
									  user.getRecommend());
	}
	
	//== DB에서 id에 해당하는 user 정보 검색 ==//
	public Optional<User> get(String id) {
		String sql = "SELECT * FROM users WHERE id = ?";	    		
	    
	    try (Stream<User> stream = jdbcTemplate.queryForStream(sql, this.userMapper, id)) {
	        return stream.findFirst();
	    } catch (DataAccessException e) {
	        return Optional.empty();
	    }
	}

	//== 테이블 전체 데이터 삭제 ==//
	public void deleteAll() {
		String sql = "DELETE FROM users";
		//콜백 객체 생성을 내장 함수가 담당한다.
		this.jdbcTemplate.update(sql);
	}
	
	//== 테이블 정보 개수 조회 ==//
	public int getCount(){
		String sql = "SELECT COUNT(*) FROM users";
		
		List<Integer> result = jdbcTemplate.query(sql,
				(rs, rowNum) -> rs.getInt(1));
		
		return (int)DataAccessUtils.singleResult(result);
	}
	
	//== 테이블에 있는 전체 User 정보 가져오기
	public List<User> getAll() {
		String sql = "SELECT * FROM users ORDER BY id DESC";
		
		return this.jdbcTemplate.query(sql, this.userMapper);
	}
}
