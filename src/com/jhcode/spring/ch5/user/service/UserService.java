package com.jhcode.spring.ch5.user.service;

import java.util.List;

import com.jhcode.spring.ch5.user.dao.UserDao;
import com.jhcode.spring.ch5.user.domain.Level;
import com.jhcode.spring.ch5.user.domain.User;

public class UserService {
	
	private UserDao userDao;
	
	//UserDao 주입
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	//로그인과 추천수에 따라 사용자의 레벨을 업그레이드하는 비즈니스 코드
	public void upgradeLevels() {
		List<User> users = userDao.getAll();
		
		for(User user : users) {
			//Level이 변화되었는지 체크하는 변수
			Boolean changed = null;
			
			//BASIC -> SIVER
			if (user.getLevel() == Level.BASIC && user.getLogin() >= 50) {
				user.setLevel(Level.SILVER);
				changed = true;
				
			//SIVER -> GOLD
			} else if (user.getLevel() == Level.SILVER && user.getRecommend() >= 30) {
				user.setLevel(Level.GOLD);
				changed = true;
				
			//GOLD = Not Changed
			} else if (user.getLevel() == Level.GOLD) {
				changed = false;
			
			//Besides.. Not Changed
			} else {
				changed = false;
			}
			
			//변화가 있을 경우만 DB에 값 변경
			
			if(changed) {
				userDao.update(user);
			}
		}
	}
	
	//== 처음 사용자에게 BASIC Level 부여 ==//
	public void add(User user) {
		if (user.getLevel() == null) user.setLevel(Level.BASIC);
		userDao.add(user);
	}
	
}
