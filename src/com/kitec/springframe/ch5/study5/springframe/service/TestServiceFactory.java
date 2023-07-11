package com.kitec.springframe.ch5.study5.springframe.service;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.kitec.springframe.ch5.study5.springframe.dao.UserDaoJdbc;

@Configuration
public class TestServiceFactory {
	@Bean
	public DataSource dataSource() {
		
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		
		dataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class);
		dataSource.setUrl("jdbc:mysql://localhost:3306/testdb?characterEncoding=UTF-8");
		dataSource.setUsername("root");
		dataSource.setPassword("1234");

		return dataSource;
	}

	@Bean
	public UserDaoJdbc userDao() {
		UserDaoJdbc userDaoJdbc = new UserDaoJdbc();
		userDaoJdbc.setDataSource(dataSource());
		return userDaoJdbc;
	}
	
	@Bean
	public UserService userService() {
		UserService userService = new UserService();
		userService.setUserDao(userDao());
		userService.setTransactionManager(transactionManager());
		
		// mailSender 의존성 주입
		// 기본적인 Test는 DummyMailSender 객체를 사용하여 테스트한다
		userService.setMailSender(mailSender());
		return userService;
	}
	
	@Bean
	public DummyMailSender mailSender() {
		DummyMailSender dummyMailSender = new DummyMailSender();
		return dummyMailSender;
	}
	
	// 메일이 진짜 전송되는지는 SendToGamilUpgradeTest에서 Java MailSenderImpl을 수동 DI하여 설정한다
	// 나중에 JavaMailSenderImpl 객체를 userServiec()에서 DI하여 실제 메일을 수행하도록 바로 변경 가능
	@Bean
	public JavaMailSenderImpl jMailSender() {
		
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
	    mailSender.setPort(587);  // TLS : 587, SSL : 465
	    mailSender.setUsername("jhcode33@gmail.com");
	    mailSender.setPassword("nullcpthfmqvbbpf");
	    
	    //JavaMailSenderImpl은 JavaMailProperties를 통해 Session 객체를 자동으로 구성함.
	    mailSender.setJavaMailProperties(props());
	    
		return mailSender;
	}
	
	@Bean
	public Properties props() {
		Properties props = new Properties();
		
		props.put("mail.smtp.auth", "true");			//Gmail SMTP 서버 인증 활성화
        props.put("mail.smtp.starttls.enable", "true"); //starttls : 보안 연결 설정
        return props;
	}
	
	@Bean
	public DataSourceTransactionManager transactionManager() {
		DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
		dataSourceTransactionManager.setDataSource(dataSource());
		return dataSourceTransactionManager;
	}
}
