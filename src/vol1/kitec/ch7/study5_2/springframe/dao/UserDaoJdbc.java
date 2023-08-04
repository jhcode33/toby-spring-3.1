package vol1.kitec.ch7.study5_2.springframe.dao;

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

import vol1.kitec.ch7.study5_2.springframe.domain.Level;
import vol1.kitec.ch7.study5_2.springframe.domain.User;
import vol1.kitec.ch7.study5_2.springframe.sqlservice.SqlService;

public class UserDaoJdbc implements UserDao {	
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	private JdbcTemplate jdbcTemplate;
	
	private SqlService sqlService;

	public void setSqlService(SqlService sqlService) {
		this.sqlService = sqlService;
	}
	
	private RowMapper<User> userMapper = 
			new RowMapper<User>() {
					public User mapRow(ResultSet rs, int rowNum) throws SQLException {
						User user = new User();
						user.setId(rs.getString("id"));
						user.setName(rs.getString("name"));
						user.setPassword(rs.getString("password"));
						user.setEmail(rs.getString("email"));
						user.setLevel(Level.valueOf(rs.getInt("level")));
						user.setLogin(rs.getInt("login"));
						user.setRecommend(rs.getInt("recommend"));
						return user;
				}
			};

	@Override
	public void add(User user) {
		this.jdbcTemplate.update(
				this.sqlService.getSql("userAdd"),
				user.getId(), user.getName(), user.getPassword(), user.getEmail(), 
				user.getLevel().intValue(), user.getLogin(), user.getRecommend());
	}

	@Override
	public Optional<User> get(String id) {
			    
	    try (Stream<User> stream = jdbcTemplate.queryForStream(this.sqlService.getSql("userGet"), 
	    		this.userMapper, id)) {
	        return stream.findFirst();
	    } catch (DataAccessException e) {
	        return Optional.empty();
	    }
	}
	
	@Override
	public void deleteAll() {
		this.jdbcTemplate.update(this.sqlService.getSql("userDeleteAll"));
	}
	
	@Override
	public int getCount() {
		List<Integer> result = jdbcTemplate.query(this.sqlService.getSql("userGetCount"), 
	    		(rs, rowNum) -> rs.getInt(1));
	    return (int) DataAccessUtils.singleResult(result);
	}

	@Override
	public List<User> getAll() {
		return this.jdbcTemplate.query(this.sqlService.getSql("userGetAll"),
				this.userMapper
		);
	}	

	@Override
	public void update(User user) {
		this.jdbcTemplate.update(
				this.sqlService.getSql("userUpdate"), 
				user.getName(), user.getPassword(), user.getEmail(), 
				user.getLevel().intValue(), user.getLogin(), user.getRecommend(),
				user.getId());	
	}
}