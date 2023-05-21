package com.jhcode.spring.ch2.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.jhcode.spring.ch2.domain.User;

@ExtendWith(SpringExtension.class) //@RunWith(SpringRunner.class) 와 동일하다
@ContextConfiguration(locations="/applicationContext.xml")
public class UserDaoTest {
	
	@Autowired
	private ApplicationContext context;
	
	@Autowired
	private UserDao dao;
	
	@BeforeEach
    public void setUp() {
		System.out.println(this.context);
        System.out.println(this);
    }
	
	
	@Test
	public void addAndGet() throws ClassNotFoundException, SQLException {
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
		dao.deleteAll();
		assertEquals(dao.getCount(), 0);

		assertThrows(EmptyResultDataAccessException.class, () -> {dao.get("unknown_id");});
	}
}
