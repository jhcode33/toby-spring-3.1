package vol1.jhcode.ch2.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;

import vol1.jhcode.ch2.user.domain.User;


public class UserDao {
	//== DataSource 사용하기 ==//
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public void add(User user) throws ClassNotFoundException, SQLException{
		Connection con = dataSource.getConnection();
		
		//프리페어 스테이트먼츠 사용
		String sql = "INSERT INTO users(id, name, password) values(?,?,?)";
		PreparedStatement pst = con.prepareStatement(sql);
		pst.setString(1, user.getId());
		pst.setString(2, user.getName());
		pst.setString(3, user.getPassword());
		
		pst.executeUpdate();
		
		pst.close();
		con.close();
		
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
	
	//== 테이블 정보 삭제 ==//
	public void deleteAll() throws SQLException {
		Connection con = dataSource.getConnection();
		
		String sql = "DELETE FROM users";
		PreparedStatement pst = con.prepareStatement(sql);
		pst.executeUpdate();
		
		pst.close();
		con.close();
	}
	
	//== 테이블 정보 개수 조회 ==//
	public int getCount() throws SQLException {
		Connection con = dataSource.getConnection();
		
		String sql = "SELECT COUNT(*) FROM users";
		PreparedStatement pst = con.prepareStatement(sql);
		ResultSet rs = pst.executeQuery();
		rs.next();
		int result = rs.getInt(1);
		
		rs.close();
		pst.close();
		con.close();
		
		return result;
	}
}
