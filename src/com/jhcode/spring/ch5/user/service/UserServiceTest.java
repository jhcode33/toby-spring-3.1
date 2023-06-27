package com.jhcode.spring.ch5.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.jhcode.spring.ch5.user.dao.UserDao;
import com.jhcode.spring.ch5.user.domain.Level;
import com.jhcode.spring.ch5.user.domain.User;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestServiceFactory.class})
public class UserServiceTest {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserDao userDao;
	
	List<User> users;
	
//	@Test
//	public void bean() {
//		assertNotNull(userService);
//	}
	
	@BeforeEach
	public void setUp() {
		
		//배열을 리스트로 만들어주는 메소드
		users = Arrays.asList(
				new User("user1", "user1", "p1", Level.BASIC, 49, 0),
				new User("user2", "user2", "p2", Level.BASIC, 50, 0),
				new User("user3", "user3", "p3", Level.SILVER, 60, 29),
				new User("user4", "user4", "p4", Level.SILVER, 60, 30),
				new User("user5", "user5", "p5", Level.GOLD, 100, 100)
				);
	}
	
	//User의 Level과 인수로 받은 Level의 값을 비교하는 메소드
	private void checkLevel(User user, Level expectedLevel) {
		Optional<User> optionalUser = userDao.get(user.getId());
		
		if(optionalUser != null) {
			User userUpdate = optionalUser.get();
			assertEquals(userUpdate.getLevel(), expectedLevel);
		}
	}
	
	@Test
	public void upgradeLevels() {
		userDao.deleteAll();
		
		for(User user : users) {
			userDao.add(user);
		}
		
		//DB의 모든 User을 가지고와서 Level 등급을 조정함
		userService.upgradeLevels();
		
		checkLevel(users.get(0), Level.BASIC);
		checkLevel(users.get(1), Level.SILVER);
		checkLevel(users.get(2), Level.SILVER);
		checkLevel(users.get(3), Level.GOLD);
		checkLevel(users.get(4), Level.GOLD);
	}
	
	@Test
	public void add() {
		userDao.deleteAll();
		
		User userWithLevel = users.get(4); //GOLD
		User userWithOutLevel = users.get(0); //BASIC
		userWithOutLevel.setLevel(null); //BASIC -> NULL, 비어있으면 다시 BASIC으로 설정되어야함.
		
		//GOLD -> GOLD 그대로 유지
		userService.add(userWithLevel);
		
		//Null -> BASIC 처음 가입 유저는 BASIC으로 설정
		userService.add(userWithOutLevel);
		
		//DB에 저장된 것을 불러와서 저장한 값이랑 비교함.
		Optional<User> optionalLevelUser = userDao.get(userWithLevel.getId());
		if(optionalLevelUser != null) {
			User userWithLevelRead = optionalLevelUser.get();
			assertEquals(userWithLevelRead.getLevel(), userWithLevel.getLevel());
		}
		
		Optional<User> optionalOutLevelUser = userDao.get(userWithOutLevel.getId());
		if(optionalOutLevelUser != null) { 
			User userWithOutLevelRead = optionalOutLevelUser.get();
			assertEquals(userWithOutLevelRead.getLevel(), userWithOutLevel.getLevel());
		}
	}
}
