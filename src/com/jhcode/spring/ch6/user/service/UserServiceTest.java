package com.jhcode.spring.ch6.user.service;

import static com.jhcode.spring.ch6.user.service.UserServiceImpl.MIN_LOGCOUNT_FOR_SILVER;
import static com.jhcode.spring.ch6.user.service.UserServiceImpl.MIN_RECCOMEND_FOR_GOLD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;

import com.jhcode.spring.ch6.user.dao.UserDao;
import com.jhcode.spring.ch6.user.domain.Level;
import com.jhcode.spring.ch6.user.domain.User;
import com.jhcode.spring.ch6.user.service.TestUserService.TestUserServiceException;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestServiceFactory.class})
public class UserServiceTest {
	
	@Autowired private UserDao userDao;
	@Autowired private UserService userService;
	@Autowired private UserServiceImpl userServiceImpl;
	@Autowired private PlatformTransactionManager transactionManager;
	@Autowired private MailSender mailSender;
	
	List<User> users;
	
	@BeforeEach
	public void setUp() {
		
		//배열을 리스트로 만들어주는 메소드
		users = Arrays.asList(
				new User("user1", "user1", "p1", "user1@go.kr", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER -1, 0),
				new User("user2", "user2", "p2", "user2@go.kr", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER, 0),
				new User("user3", "user3", "p3", "user3@go.kr", Level.SILVER, 60, MIN_RECCOMEND_FOR_GOLD -1),
				new User("user4", "user4", "p4", "user4@go.kr", Level.SILVER, 60, MIN_RECCOMEND_FOR_GOLD),
				new User("user5", "user5", "p5", "user5@go.kr", Level.GOLD, 100, 100)
				);
	}
	
	//User의 Level과 인수로 받은 Level의 값을 비교하는 메소드
	//어떤 레벨로 바뀌는지가 아니라, 다음 레벨로 바뀔 것인지를 확인한다.
	private void checkLevel(User user, boolean upgraded) {
		Optional<User> optionalUser = userDao.get(user.getId());
		
		if(optionalUser != null) {
			User userUpdate = optionalUser.get();
			
			if(upgraded) {
				System.out.println(user.getId() + " : Level 업그레이드 되었음");
				assertEquals(userUpdate.getLevel(), user.getLevel().nextLevel());
			
			} else {
				System.out.println(user.getId() + " : Level 업그레이드 되지 않음");
				assertEquals(userUpdate.getLevel(), user.getLevel());
			}
			
		}
	}
	
	//== Mockito를 사용한 테스트 코드 ==//
	@Test @DirtiesContext
	public void mockUpgradeLevels() throws Exception {
		UserServiceImpl userServiceImple = new UserServiceImpl();
		
		//mockito를 사용한 Mock 객체 생성 및 주입
		UserDao mockUserDao = mock(UserDao.class);
		when(mockUserDao.getAll()).thenReturn(this.users);
		userServiceImpl.setUserDao(mockUserDao);
		
		MailSender mockMailSender = mock(MailSender.class);
		userServiceImpl.setMailSender(mockMailSender);
		
		userServiceImpl.upgradeLevels();
		
		//mockUserDao의 update 메소드가 2번 실행되는지 검증, 인수로 User타입의 객체를 임의로(any)로 전달함
		verify(mockUserDao, times(2)).update(any(User.class));
		
		//mockUserDao가 update 메소드를 실행할 때 인자로 users.get(1)를 인스턴스로 전달하는지 확인함
		verify(mockUserDao).update(users.get(1));
		assertEquals(users.get(1).getLevel(), Level.SILVER);
		
		//mockUserDao가 update 메소드를 실행할 때 인자로 users.get(3)를 인스턴스로 전달하는지 확인함
		verify(mockUserDao).update(users.get(3));
		assertEquals(users.get(3).getLevel(), Level.GOLD);
		
		//ArgumentCaptor 객체가 SimpleMailMessage 인스턴스를 저장할 수 있도록 설정함
		ArgumentCaptor<SimpleMailMessage> mailMessageArg =
				ArgumentCaptor.forClass(SimpleMailMessage.class);
		
		//mockMailSender의 send 메소드가 2번 실행되었는지, 
		//send() 메소드가 실행될 때 SimpleMailMessage 객체가 전달되었는지 확인
		verify(mockMailSender, times(2)).send(mailMessageArg.capture());
		List<SimpleMailMessage> mailMessages = mailMessageArg.getAllValues();
		
		assertEquals(mailMessages.get(0).getTo()[0], users.get(1).getEmail());
		assertEquals(mailMessages.get(1).getTo()[0], users.get(3).getEmail());
		
	}
	
	//== upgradeLevels() 테스트에 사용될 MailSender Mock 객체 ==//
	static class MockMailSender implements MailSender {
		private List<String> requests = new ArrayList<String>();	
		
		public List<String> getRequests() {
			return requests;
		}

		public void send(SimpleMailMessage mailMessage) throws MailException {
			requests.add(mailMessage.getTo()[0]);  
		}

		public void send(SimpleMailMessage[] mailMessage) throws MailException {
		}
	}
	
	//== upgradeLevels() 테스트에 사용될 UserDao Mock 객체 ==//
	static class MockUserDao implements UserDao {
		//업그레이드 후보와 업그레이드 된 결과를 저장할 변수
		private List<User> users;
		private List<User> updated = new ArrayList();
		
		//생성자
		private MockUserDao(List<User> users) {
			this.users = users;
		}
		
		private List<User> getUpdated(){
			return this.updated;
		}
		
		//== 스텁 기능 제공 ==//
		public List<User> getAll(){
			return this.users;
		}
		
		//== Mock Oject 기능 제공 ==//
		public void update(User user) {
			updated.add(user);
		}
		
		//사용되지 않는 기능, UnsupportedOperationException을 발생시키는 것이 좋다
		public void add(User user) { throw new UnsupportedOperationException(); }
		public void deleteAll() { throw new UnsupportedOperationException(); }
		public Optional<User> get(String id) { throw new UnsupportedOperationException(); }
		public int getCount() { throw new UnsupportedOperationException(); }
	}
	
	@Test
	public void upgradeLevels() throws Exception {
		UserServiceImpl userServiceImpl = new UserServiceImpl();
		
		MockUserDao mockUserDao = new MockUserDao(this.users);
		userServiceImpl.setUserDao(mockUserDao);
		
		MockMailSender mockMailSender = new MockMailSender();
		userServiceImpl.setMailSender(mockMailSender);
		
		userServiceImpl.upgradeLevels();
		
		List<User> updated = mockUserDao.getUpdated();
		assertEquals(updated.size(), 2);
		checkUserAndLevel(updated.get(0), "user2", Level.SILVER);
		checkUserAndLevel(updated.get(1), "user4", Level.GOLD);
		
		List<String> request = mockMailSender.getRequests();  
		assertEquals(request.size(), 2);
		assertEquals(request.get(0), users.get(1).getEmail());
		assertEquals(request.get(1), users.get(3).getEmail());
	}
	
	//== id와 level을 확인하는 헬퍼 메소드 ==//
	private void checkUserAndLevel(User updated, String expectedId, Level expectedLevel) {
		assertEquals(updated.getId(), expectedId);
		assertEquals(updated.getLevel(), expectedLevel);
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
	
	//예외 발생 시 작업 취소 여부 테스트
	@Test
	public void upgradeAllorNothing() throws Exception {
		UserServiceImpl testUserService = new TestUserService(users.get(3).getId());
		testUserService.setUserDao(userDao);
		testUserService.setMailSender(mailSender);
		
//		UserServiceTx txUserService = new UserServiceTx();
//		txUserService.setTransactionManager(transactionManager);
//		txUserService.setUserService(testUserService);
		
		TransactionHandler txHandler = new TransactionHandler();
		txHandler.setTarget(testUserService); 					//타겟 오브젝트 주입, 핵심 기능을 위임할 오브젝트
		txHandler.setTransactionManager(transactionManager);    //트랜잭션 기능에 필요한 객체 주입
		txHandler.setPattern("upgradeLevels"); 					//트랜잭션 기능을 부가할 메소드 이름
		
		UserService txUserService = 
				(UserService)Proxy.newProxyInstance(getClass().getClassLoader(),     //현재 클래스의 로더를 가져옴
													new Class[] {UserService.class}, //프록시 객체가 구현해야할 인터페이스
													txHandler);						 //구현된 프록시 객체에 부가기능을 주고 위임할 invocation
		
		userDao.deleteAll();
		
		for (User user : users) {
			testUserService.add(user);
		}
		
		try {
			txUserService.upgradeLevels();
			//테스트가 제대로 동작하게 하기 위한 안전장치, 로직을 잘못짜서 upgradeLevels() 메소드가 통과되도 무조건 실패함.
			//fail("TestUserServiceException expected");
		} catch (TestUserServiceException e) {
			System.out.println("TestUserServiceException 예외 발생함");
		} finally {
			checkLevel(users.get(1), false);
		}
	}
	
	//@Test
	public void sendEmailToGmail() throws UnsupportedEncodingException {
		String host = "smtp.gmail.com";
		int port = 587;
		String username = "메일을 전송할 아이디";
		String password = "앱 어플리케이션 비밀번호";
		
		// 수진자 이메일 주소
		String toAddress = "메일을 받을 아이디";
		
		// 메일 속성 설정
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.prot", port);
		
		// 인증 객체 생성
		Authenticator authenticator = new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		};
		
		// 세션 생성
		Session session = Session.getInstance(props, authenticator);
		
		try {
			MimeMessage message = new MimeMessage(session);
		
			message.setFrom(new InternetAddress(username));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));
			message.setSubject(MimeUtility.encodeText("반가워요", "UTF-8", "B"));
			message.setText("테스트 메일입니다.", "UTF-8");
			
			// 메일 전송
			Transport.send(message);
			
			System.out.println("Email sent successfully!");
		} catch (Exception e) {
			System.out.println("Failed to send email. Error message: " + e.getMessage());
			fail("This sendEmailToFmail test is failed!!");
		}
	}
}
