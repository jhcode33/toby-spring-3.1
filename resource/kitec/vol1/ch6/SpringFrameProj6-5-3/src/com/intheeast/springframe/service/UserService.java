package com.intheeast.springframe.service;

import java.util.List;
import java.util.Optional;

import com.intheeast.springframe.domain.User;

public interface UserService {
	void add(User user);
	void upgradeLevels();
}

