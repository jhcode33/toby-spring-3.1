package com.jhcode.spring.ch7.user.service;

import com.jhcode.spring.ch6.user.domain.User;

public interface UserService {
	
	// 트랜잭션과 비즈니스 로직을 분리하기 위한 인터페이스
	void add(User user);
	void upgradeLevels();

}
