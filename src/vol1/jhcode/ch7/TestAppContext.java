package vol1.jhcode.ch7;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;

import vol1.jhcode.ch7.user.service.DummyMailSender;
import vol1.jhcode.ch7.user.service.UserService;
import vol1.jhcode.ch7.user.service.UserServiceTest.TestUserService;

@Configuration
public class TestAppContext {
	
	@Bean
	public UserService testUserSerivce() {
		TestUserService testService = new TestUserService();
		return testService;
	}

	@Bean
	public MailSender mailSender() {
		return new DummyMailSender();
	}
}
