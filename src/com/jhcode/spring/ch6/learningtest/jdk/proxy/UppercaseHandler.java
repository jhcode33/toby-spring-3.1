package com.jhcode.spring.ch6.learningtest.jdk.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class UppercaseHandler implements InvocationHandler {

	Hello target;
	
	public UppercaseHandler(Hello target) {
		this.target = target;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		//타겟으로 위임, 인터페이스의 모든 메소드 호출에 적용
		String ret = (String)method.invoke(target, args);

		// 부가기능 제공
		return ret.toUpperCase();
	}
}
