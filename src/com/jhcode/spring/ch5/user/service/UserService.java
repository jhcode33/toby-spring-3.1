package com.jhcode.spring.ch5.user.service;

import java.util.List;

import com.jhcode.spring.ch5.user.dao.UserDao;
import com.jhcode.spring.ch5.user.domain.Level;
import com.jhcode.spring.ch5.user.domain.User;

public class UserService {
	public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
	public static final int MIN_RECOMMEND_FOR_GOLD = 30;
	
	
	private UserDao userDao;
	
	//UserDao 주입
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	//로그인과 추천수에 따라 사용자의 레벨을 업그레이드하는 비즈니스 코드
	public void upgradeLevels() {
		List<User> users = userDao.getAll();
		
		for(User user : users) {
			if (canUpgradeLevel(user)) {
				upgradeLevel(user);
			}
		}
	}
	
	//== 업그레이드가 가능한지 확인하는 코드 ==//
	private boolean canUpgradeLevel(User user) {
		Level currentLevel = user.getLevel();
		
		switch(currentLevel) {
		case BASIC : return (user.getLogin() >= MIN_LOGCOUNT_FOR_SILVER);
		case SILVER : return (user.getRecommend() >= MIN_RECOMMEND_FOR_GOLD);
		case GOLD : return false;
		default : throw new IllegalArgumentException("Unknown Level : " + currentLevel);
		}
	}
	
	//== 업그레이드가 가능할 때 실제로 값을 변경하는 코드 ==//
	private void upgradeLevel(User user) {
		user.upgradeLevel();
		userDao.update(user);
	}
	
	
	//== 처음 사용자에게 BASIC Level 부여 ==//
	public void add(User user) {
		if (user.getLevel() == null) user.setLevel(Level.BASIC);
		userDao.add(user);
	}
	
}
