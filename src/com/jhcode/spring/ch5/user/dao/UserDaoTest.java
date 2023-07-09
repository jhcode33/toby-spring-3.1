package com.jhcode.spring.ch5.user.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.jhcode.spring.ch5.user.domain.Level;
import com.jhcode.spring.ch5.user.domain.User;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DaoFactory.class})
public class UserDaoTest {
	
	@Autowired
	private UserDaoJdbc dao;
	
	@Autowired 
	private DataSource dataSource;
	
	private User user1;
	private User user2;
	private User user3;
	
	@BeforeEach
    public void setUp() {
        this.user1 = new User("user1", "one", "1111", Level.BASIC, 1, 1, "user1@go.ck");
        this.user2 = new User("user2", "two", "2222", Level.SILVER, 55, 2, "user2@go.ck");
        this.user3 = new User("user3", "three", "3333", Level.GOLD, 100, 3, "user3@go.ck");
    }
	
	@Test
	public void addAndGet() throws ClassNotFoundException, SQLException {
        System.out.println("addAndGet(): " + this);
		dao.deleteAll();	
		assertEquals(dao.getCount(), 0);
		
		dao.deleteAll();
		assertEquals(dao.getCount(), 0);

		dao.add(user1);
		dao.add(user2);
		assertEquals(dao.getCount(), 2);
		
		Optional<User> Optuserget1 = dao.get(user1.getId());
		if(!Optuserget1.isEmpty()) {
			User userget = Optuserget1.get();
			assertEquals(user1.getName(), userget.getName());
			assertEquals(user1.getPassword(), userget.getPassword());
		}
		
		Optional<User> Optuserget2 = dao.get(user2.getId());
		if(!Optuserget2.isEmpty()) {
			User userget = Optuserget2.get();
			assertEquals(user2.getName(), userget.getName());
			assertEquals(user2.getPassword(), userget.getPassword());
		}
	}
	
	@Test
	public void count() throws ClassNotFoundException, SQLException {
        System.out.println("count(): " + this);
		
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

		//assertThrows(EmptyResultDataAccessException.class, () -> {dao.get("unknown_id");});
		Optional<User> Optuserget = dao.get("unknown_id");
		assertTrue(Optuserget.isEmpty());
	}
	
	@Test
	public void getAll() throws ClassNotFoundException, SQLException{
		dao.deleteAll();
		
		//== 네거티브 테스트, DB에 데이터가 하나도 없을 경우 ==//
		dao.add(user1);
		List<User> users1 = dao.getAll();
		assertEquals(users1.size(), 1);
		checkSameUser(user1, users1.get(0));
		
		dao.add(user2); 
		List<User> users2 = dao.getAll();
		assertEquals(users2.size(), 2);
		checkSameUser(user2, users2.get(0));  
		checkSameUser(user1, users2.get(1));
		
		dao.add(user3); 
		List<User> users3 = dao.getAll();
		assertEquals(users3.size(), 3);	
		checkSameUser(user3, users3.get(0));  
		checkSameUser(user2, users3.get(1));  
		checkSameUser(user1, users3.get(2));
		
	}
	
	//DB에서 가져온 값과, 로직에서 생성한 값이 같은지 확인하는 코드
	private void checkSameUser(User user1, User user2) {
		assertEquals(user1.getId(), user2.getId());
		assertEquals(user1.getName(), user2.getName());
		assertEquals(user1.getPassword(), user2.getPassword());
		
		assertEquals(user1.getLevel(), user2.getLevel());
		assertEquals(user1.getLogin(), user2.getLogin());
		assertEquals(user1.getRecommend(), user2.getRecommend());
	}
	
	@Test
	public void sqlExceptionTranslate() {
	    // 데이터베이스의 모든 데이터 삭제
	    dao.deleteAll();

	    try {
	        // 동일한 사용자(user1)를 두 번 추가하여 중복 키 예외 발생
	        dao.add(user1);
	        dao.add(user1);
	    } catch (DuplicateKeyException ex) {
	        // DuplicateKeyException의 원인인 SQLException 객체를 가져옴
	        SQLException sqlEx = (SQLException) ex.getCause();
	        
	        // SQLErrorCodeSQLExceptionTranslator를 사용하여 SQLException 번역
	        SQLExceptionTranslator set = new SQLErrorCodeSQLExceptionTranslator(this.dataSource);
	        DataAccessException transEx = set.translate(null, null, sqlEx);

	        // 번역된 예외의 클래스가 DuplicateKeyException인지 확인
	        assertEquals(DuplicateKeyException.class, transEx.getClass());
	    }
	}
	
	@Test
	public void update() {
		dao.deleteAll();
		
		dao.add(user1); //수정할 사용자
		dao.add(user2); //수정하지 않을 사용자
		
		user1.setName("jhcode33");
		user1.setPassword("password");
		user1.setLevel(Level.SILVER);
		user1.setLogin(1000);
		user1.setRecommend(999);
		dao.update(user1);
		
		Optional<User> Optuser1Update = dao.get(user1.getId());
		
		if(Optuser1Update != null) {
			User user1Update = Optuser1Update.get();
			checkSameUser(user1, user1Update);
		}
		
		Optional<User> Optuser2Update = dao.get(user2.getId());
		
		if(Optuser2Update != null) {
			User user2Update = Optuser2Update.get();
			checkSameUser(user2, user2Update);
		}
	}
}
