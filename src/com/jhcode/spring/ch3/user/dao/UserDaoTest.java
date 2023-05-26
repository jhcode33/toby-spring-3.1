package com.jhcode.spring.ch3.user.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import com.jhcode.spring.ch3.user.domain.User;


@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class UserDaoTest {
	
	private UserDao dao;
	
	@BeforeEach
    public void setUp() {
        System.out.println("setUp():" + this);
        
       
        DataSource dataSource = new SingleConnectionDataSource(
					        		"jdbc:mariadb://localhost:3306/toby?characterEncoding=UTF-8",
					        		"root",
					        		"1234",
					        		true);
        dao = new UserDao();
        dao.setDataSource(dataSource);
    }
	
	@Test
	public void addAndGet() throws ClassNotFoundException, SQLException {
        System.out.println("addAndGet(): " + this);
		dao.deleteAll();	
		assertEquals(dao.getCount(), 0);
		
		User user1 = new User("user1", "one", "1111");
		User user2 = new User("user2", "two", "2222");
		
		dao.deleteAll();
		assertEquals(dao.getCount(), 0);

		dao.add(user1);
		dao.add(user2);
		assertEquals(dao.getCount(), 2);
		
		User userget1 = dao.get(user1.getId());		
		assertEquals(userget1.getName(), user1.getName());
		assertEquals(userget1.getPassword(), user1.getPassword());
		
		User userget2 = dao.get(user2.getId());		
		assertEquals(userget2.getName(), user2.getName());
		assertEquals(userget2.getPassword(), user2.getPassword());
	}
	
	@Test
	public void count() throws ClassNotFoundException, SQLException {
        System.out.println("count(): " + this);
		User user1 = new User("user1", "one", "1111");
		User user2 = new User("user2", "two", "2222");
		User user3 = new User("user3", "three", "3333");
		
		dao.deleteAll();
		assertEquals(dao.getCount(), 0);
		
		dao.add(user1);
		assertEquals(dao.getCount(), 1);
		
		dao.add(user2);
		assertEquals(dao.getCount(), 2);
		
		dao.add(user3);
		assertEquals(dao.getCount(), 3);
	}
	
	@Test
	public void getUserFailure() throws SQLException{
        System.out.println("getUserFailure(): " + this);
		dao.deleteAll();
		assertEquals(dao.getCount(), 0);

		assertThrows(EmptyResultDataAccessException.class, () -> {dao.get("unknown_id");});
	}
}
