package vol1.jhcode.ch2.learningtest.junit;

import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;

import vol1.jhcode.ch2.user.dao.UserDao;

@ContextConfiguration
public class ApplicationContextTest {
	
	@Bean
	public UserDao userDao() {
		UserDao userDao = new UserDao();
		return userDao;
	}
}
