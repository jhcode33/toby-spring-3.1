package com.jhcode.spring.ch6.learningtest.jdk.proxy;

public class HelloUppercase implements Hello {
	
	//다른 프록시로 접근할 수도 있기 때문에 인터페이스 타입으로 받는다
	Hello hello;
	
	public HelloUppercase(Hello hello) {
		this.hello = hello;
	}
	
	
	@Override
	public String sayHello(String name) {
		//기존의 기능에 새로운 기능을 추가해서 위임한다
		return hello.sayHello(name).toUpperCase();
	}

	@Override
	public String sayHi(String name) {
		return hello.sayHi(name).toUpperCase();
	}

	@Override
	public String sayThankYou(String name) {
		return hello.sayThankYou(name).toUpperCase();
	}

}
