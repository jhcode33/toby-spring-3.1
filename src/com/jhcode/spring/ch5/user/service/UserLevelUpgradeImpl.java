package com.jhcode.spring.ch5.user.service;

import com.jhcode.spring.ch5.user.dao.UserDao;
import com.jhcode.spring.ch5.user.domain.Level;
import com.jhcode.spring.ch5.user.domain.User;

public class UserLevelUpgradeImpl implements UserLevelUpgradePolicy {
	
	public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
	public static final int MIN_RECOMMEND_FOR_GOLD = 30;

	@Override
	public boolean canUpgradeLevel(User user) {
		Level currentLevel = user.getLevel();
		
		switch(currentLevel) {
		case BASIC : return (user.getLogin() >= MIN_LOGCOUNT_FOR_SILVER);
		case SILVER : return (user.getRecommend() >= MIN_RECOMMEND_FOR_GOLD);
		case GOLD : return false;
		default : throw new IllegalArgumentException("Unknown Level : " + currentLevel);
		}
	}

	@Override
	public void upgradeLevel(User user, UserDao userDao) {
		user.upgradeLevel();
		userDao.update(user);
	}

}
