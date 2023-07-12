package com.jhcode.spring.ch6.learningtest.jdk.proxy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Proxy;

import org.junit.jupiter.api.Test;

public class DynamicProxyTest {
	
	@Test
	public void simpleProxy() {
		Hello hello = new HelloTarget(); // 타겟은 인터페이스를 통해서 접근한다
		
		assertEquals(hello.sayHello("Toby"), "Hello Toby");
		assertEquals(hello.sayHi("Toby"), "Hi Toby");
		assertEquals(hello.sayThankYou("Toby"), "Thank You Toby");
		
		
		Hello proxiedHello = new HelloUppercase(new HelloTarget());
		
		assertEquals(proxiedHello.sayHello("Toby"), "HELLO TOBY");
		assertEquals(proxiedHello.sayHi("Toby"), "HI TOBY");
		assertEquals(proxiedHello.sayThankYou("Toby"), "THANK YOU TOBY");
		
	}
	
	@Test
	public void dynamicProxy() {
		Hello dynamicProxy = (Hello)Proxy.newProxyInstance(getClass().getClassLoader(),
															new Class[] { Hello.class },
															new UppercaseHandler(new HelloTarget()));
		
		assertEquals(dynamicProxy.sayHello("Toby"), "HELLO TOBY");
		assertEquals(dynamicProxy.sayHi("Toby"), "HI TOBY");
		assertEquals(dynamicProxy.sayThankYou("Toby"), "THANK YOU TOBY");
	}

}
