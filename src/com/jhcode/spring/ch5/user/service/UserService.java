package com.jhcode.spring.ch5.user.service;

import java.util.List;

import com.jhcode.spring.ch5.user.dao.UserDao;
import com.jhcode.spring.ch5.user.domain.Level;
import com.jhcode.spring.ch5.user.domain.User;

public class UserService {
	
	private UserLevelUpgradePolicy userLevleUpgrade;
	private UserDao userDao;
	
	//UserDao 주입
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	//UserLevelUpgradePolicy 주입
	public void setUserLevelUpgradePolicy(UserLevelUpgradePolicy userLevelUpgradePolicy) {
		this.userLevleUpgrade = userLevelUpgradePolicy;
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
		return userLevleUpgrade.canUpgradeLevel(user);
	}
	
	//== 업그레이드가 가능할 때 실제로 값을 변경하는 코드 ==//
	private void upgradeLevel(User user) {
		userLevleUpgrade.upgradeLevel(user, userDao);
	}
	
	
	//== 처음 사용자에게 BASIC Level 부여 ==//
	public void add(User user) {
		if (user.getLevel() == null) user.setLevel(Level.BASIC);
		userDao.add(user);
	}
	
}
