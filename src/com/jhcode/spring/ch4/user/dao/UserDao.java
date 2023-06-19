package com.jhcode.spring.ch4.user.dao;

import java.util.List;
import java.util.Optional;

import com.jhcode.spring.ch4.user.domain.User;

public interface UserDao {
	void add(User user);
	Optional<User> get(String id);
	List<User> getAll();
	void deleteAll();
	int getCount();
}
