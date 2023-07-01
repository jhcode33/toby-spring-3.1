package com.jhcode.spring.ch5.user.service;

import com.jhcode.spring.ch5.user.dao.UserDao;
import com.jhcode.spring.ch5.user.domain.User;

public interface UserLevelUpgradePolicy {
	
	boolean canUpgradeLevel(User user);
	void upgradeLevel(User user, UserDao userDao);

}
