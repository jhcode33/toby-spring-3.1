package com.jhcode.spring.ch2.learningtest.junit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class JUnitTest {
	
	static Set<JUnitTest> testObjects = new HashSet<JUnitTest>();
	static JUnitTest testObject;
	
	@Test
	public void test1() {
		assertFalse(testObjects.contains(this));
		testObjects.add(this);
		
		// 테스트 메소드가 매번 새로운 객체를 생성하는지 기존에 스태틱 변수에 가지고 있는 객체와 생성된 객체와 비교한다.
		// 첫 번째와 세 번째의 객체를 비교할 수 없어, 위의 코드를 만들었다.
		assertNotSame(this, testObject);
		testObject = this;
	}
	
	@Test
	public void test2() {
		assertFalse(testObjects.contains(this));
		testObjects.add(this);
		
		assertNotSame(this, testObject);
		testObject = this;
	}
	
	@Test
	public void test3() {
		assertFalse(testObjects.contains(this));
		testObjects.add(this);
		
		assertNotSame(this, testObject);
		testObject = this;
	}
}
