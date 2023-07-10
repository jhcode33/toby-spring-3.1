package com.jhcode.spring.ch6.user.service;

import java.util.List;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.jhcode.spring.ch6.user.dao.UserDao;
import com.jhcode.spring.ch6.user.domain.Level;
import com.jhcode.spring.ch6.user.domain.User;


public class UserService {
	public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
	public static final int MIN_RECCOMEND_FOR_GOLD = 30;
	
    private UserDao userDao;
	private MailSender mailSender;
	private PlatformTransactionManager transactionManager;
	
	//UserDao 주입
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	// MailSender 주입
	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	//TransactionManger을 외부에서 주입받음
	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	//== 트랜잭션 동기화 ==//
	//로그인과 추천수에 따라 전체 사용자의 레벨을 업그레이드하는 비즈니스 코드
	public void upgradeLevels() throws Exception {
		//트랜잭션 동기화 관리자를 이용해 동기화 작업 초기화
		TransactionStatus status = this.transactionManager.getTransaction(new DefaultTransactionDefinition());
		
		try {
			upgradeLevelsInternal();
			transactionManager.commit(status); //-> 정상적으로 작업을 마칠 경우 커밋
		} catch (Exception e) {
			transactionManager.rollback(status); //-> 정상적으로 작업을 마치지 않을 경우 롤백
			throw e;
		}
	}
	
	
	// 사용자 레벨 업그레이드 코드 분리
	private void upgradeLevelsInternal() {
		List<User> users = userDao.getAll();
		for (User user : users) {
			if (canUpgradeLevel(user)) {
				upgradeLevel(user);
			}
		}
	}
	
	
	//== 업그레이드가 가능한지 확인하는 코드 ==//
	private boolean canUpgradeLevel(User user) {
		Level currentLevel = user.getLevel(); 
		switch(currentLevel) {                                   
		case BASIC: return (user.getLogin() >= MIN_LOGCOUNT_FOR_SILVER); 
		case SILVER: return (user.getRecommend() >= MIN_RECCOMEND_FOR_GOLD);
		case GOLD: return false;
		default: throw new IllegalArgumentException("Unknown Level: " + currentLevel); 
		}
	}
	
	//== 업그레이드가 가능할 때 실제로 값을 변경하는 코드 ==//
	protected void upgradeLevel(User user) {
		user.upgradeLevel();
		userDao.update(user);
		sendUpgradeEMail(user);
	}
	
	
	//== 처음 사용자에게 BASIC Level 부여 ==//
	public void add(User user) {
		if (user.getLevel() == null) user.setLevel(Level.BASIC);
		userDao.add(user);
	}
	
	private void sendUpgradeEMail(User user) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		
		mailMessage.setTo(user.getEmail());
		mailMessage.setFrom("useradmin@ksug.org");
		mailMessage.setSubject("Upgrade 안내");
		mailMessage.setText("사용자님의 등급이 "+ user.getLevel().name());
			
		this.mailSender.send(mailMessage);
	}	
}
