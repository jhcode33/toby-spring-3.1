package com.jhcode.spring.ch5.user.service;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.jhcode.spring.ch5.user.dao.UserDao;
import com.jhcode.spring.ch5.user.domain.Level;
import com.jhcode.spring.ch5.user.domain.User;

public class UserService {
	
	protected UserLevelUpgradePolicy userLevleUpgrade;
	protected UserDao userDao;
//	private PlatformTransactionManager transactionManager;
	
	//UserDao 주입
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	//UserLevelUpgradePolicy 주입
	public void setUserLevelUpgradePolicy(UserLevelUpgradePolicy userLevelUpgradePolicy) {
		this.userLevleUpgrade = userLevelUpgradePolicy;
	}
	
//	//TransactionManger을 외부에서 주입받음
//	public void setTrancationManager(PlatformTransactionManager transactionManager) {
//		this.transactionManager = transactionManager;
//	}
	
	//== 트랜잭션 동기화 ==//
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	//로그인과 추천수에 따라 전체 사용자의 레벨을 업그레이드하는 비즈니스 코드
	public void upgradeLevels() throws Exception {
		//트랜잭션 동기화 관리자를 이용해 동기화 작업 초기화
		PlatformTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
		TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
		
		try {
			List<User> users = userDao.getAll();
			for(User user : users) {
				if (canUpgradeLevel(user)) {
					upgradeLevel(user);
				}
			}
			transactionManager.commit(status); //-> 정상적으로 작업을 마칠 경우 커밋
		
		} catch (Exception e) {
			
			transactionManager.rollback(status); //-> 정상적으로 작업을 마치지 않을 경우 롤백
			throw e;
		}
	}
	
	//== 업그레이드가 가능한지 확인하는 코드 ==//
	private boolean canUpgradeLevel(User user) {
		return userLevleUpgrade.canUpgradeLevel(user);
	}
	
	//== 업그레이드가 가능할 때 실제로 값을 변경하는 코드 ==//
	protected void upgradeLevel(User user) {
		userLevleUpgrade.upgradeLevel(user, userDao);
	}
	
	
	//== 처음 사용자에게 BASIC Level 부여 ==//
	public void add(User user) {
		if (user.getLevel() == null) user.setLevel(Level.BASIC);
		userDao.add(user);
	}
	
}
