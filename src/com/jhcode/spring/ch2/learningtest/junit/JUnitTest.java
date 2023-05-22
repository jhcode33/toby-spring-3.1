package com.jhcode.spring.ch2.learningtest.junit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.jhcode.spring.ch2.user.dao.UserDao;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ApplicationContextTest.class)
public class JUnitTest {
	
	@Autowired
	ApplicationContext context;
	static ApplicationContext contextObject = null;
	
	static Set<JUnitTest> testObjects = new HashSet<JUnitTest>();
	static JUnitTest testObject;
	
	@Test
	public void test1() {
		// 테스트 메소드가 매번 새로운 객체를 생성하는지 기존에 스태틱 변수에 가지고 있는 객체와 생성된 객체와 비교한다.
		// 첫 번째와 세 번째의 객체를 비교할 수 없어, Set을 사용하는 코드를 만들었다.
		assertNotSame(this, testObject);
		testObject = this;
		
		assertFalse(testObjects.contains(this));
		testObjects.add(this);
		
		assertTrue(contextObject == null || contextObject == this.context);
		contextObject = this.context;
	}
	
	@Test
	public void test2() {
		assertNotSame(this, testObject);
		testObject = this;
		
		assertFalse(testObjects.contains(this));
		testObjects.add(this);
		
		assertTrue(contextObject == null || contextObject == this.context);
		contextObject = this.context;
	}
	
	@Test
	public void test3() {
		assertNotSame(this, testObject);
		testObject = this;
		
		assertFalse(testObjects.contains(this));
		testObjects.add(this);
		
		assertTrue(contextObject == null || contextObject == this.context);
		contextObject = this.context;
	}
}
