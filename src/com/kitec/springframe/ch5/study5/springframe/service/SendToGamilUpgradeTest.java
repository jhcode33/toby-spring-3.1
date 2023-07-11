package com.kitec.springframe.ch5.study5.springframe.service;

import static com.kitec.springframe.ch5.study5.springframe.service.UserService.MIN_LOGCOUNT_FOR_SILVER;
import static com.kitec.springframe.ch5.study5.springframe.service.UserService.MIN_RECCOMEND_FOR_GOLD;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;

import com.kitec.springframe.ch5.study5.springframe.dao.UserDao;
import com.kitec.springframe.ch5.study5.springframe.domain.Level;
import com.kitec.springframe.ch5.study5.springframe.domain.User;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestServiceFactory.class})
public class SendToGamilUpgradeTest {
	@Autowired UserService userService;	
	@Autowired UserDao userDao;	
	@Autowired JavaMailSenderImpl jMailSender; 
	@Autowired PlatformTransactionManager transactionManager;
	
	List<User> users;	// test fixture
	
	@BeforeEach
	public void setUp() {	
		
		//강명성과 이상호만 Level이 업그레이드 되는 유저임
		users = Arrays.asList(
				new User("bumjin", "박범진", "p1", "l12ghks@gmail.com", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER-1, 0),
				new User("joytouch", "강명성", "p2", "l12ghks@gmail.com", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER, 0),
				new User("erwins", "신승한", "p3", "l12ghks@gmail.com", Level.SILVER, 60, MIN_RECCOMEND_FOR_GOLD-1),
				new User("madnite1", "이상호", "p4", "l12ghks@gmail.com", Level.SILVER, 60, MIN_RECCOMEND_FOR_GOLD),
				new User("green", "오민규", "p5", "l12ghks@gmail.com", Level.GOLD, 100, Integer.MAX_VALUE)
				);
	}
	
	@Test
	@DirtiesContext
	public void upgradeLevels() throws Exception {
		userDao.deleteAll();
		for(User user : users) userDao.add(user);
		
		//기본적으로 userService 객체는 스프링 컨테이너에서 DummyMailSender 객체를 주입 받는다
		//setter을 통해서 JavaMailSenderImpl을 주입하여 실제로 메일 전송이 가능하도록 한다
		userService.setMailSender(jMailSender);
		userService.setFromUsername(jMailSender.getUsername());
		userService.upgradeLevels();
		
		checkLevelUpgraded(users.get(0), false);
		checkLevelUpgraded(users.get(1), true);
		checkLevelUpgraded(users.get(2), false);
		checkLevelUpgraded(users.get(3), true);
		checkLevelUpgraded(users.get(4), false);
			
	}
	
	private void checkLevelUpgraded(User user, boolean upgraded) {
		Optional<User> optionalUser = userDao.get(user.getId());
		if (!optionalUser.isEmpty()) {
			User userUpdate = optionalUser.get();
			if (upgraded) {
				assertEquals(userUpdate.getLevel(), user.getLevel().nextLevel());
			}
			else {
				assertEquals(userUpdate.getLevel(), (user.getLevel()));
			}			
		}		
	}

}
