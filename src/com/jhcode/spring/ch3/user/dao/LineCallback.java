package com.jhcode.spring.ch3.user.dao;

public interface LineCallback<T> {
	
	T doSomethingWithLine(String line, T value);

}
