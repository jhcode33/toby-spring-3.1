package com.jhcode.spring.ch6.learningtest.jdk.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class UppercaseHandler implements InvocationHandler {

	Object target;
	
	public UppercaseHandler(Hello target) {
		this.target = target;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		//타겟으로 위임, 인터페이스의 모든 메소드 호출에 적용
		Object ret = (String)method.invoke(target, args);
		
		// 리턴 타입 확인 후 부가기능 제공
		if(ret instanceof String && method.getName().startsWith("say")) {
			return ((String)ret).toUpperCase();
		
		} else {
			return ret;
		}
	}
}
