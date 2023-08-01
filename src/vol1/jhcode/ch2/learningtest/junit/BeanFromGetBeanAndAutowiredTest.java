package vol1.jhcode.ch2.learningtest.junit;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import vol1.jhcode.ch2.user.dao.UserDao;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ApplicationContextTest.class)
public class BeanFromGetBeanAndAutowiredTest {
	
	@Autowired
	private ApplicationContext context;
	
	@Autowired
	private UserDao userDao;
	
	@Test
	public void getUserDaoTest() {
		UserDao beanFromAutowired = userDao;
		UserDao beanFromApplicationContext = context.getBean("userDao", UserDao.class);
		
		assertSame(beanFromAutowired, beanFromApplicationContext);
	}
}
