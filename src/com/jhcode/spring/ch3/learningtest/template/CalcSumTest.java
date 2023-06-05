package com.jhcode.spring.ch3.learningtest.template;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CalcSumTest {
	
	Calculator calculator;
	String numFilepath;
	
	//파일 이름과 사용되는 객체가 테스트마다 중복되기 때문에 테스트 전에 실행하는 @BeforeEach 어노테이션으로 따로 뺴두었다.
	@BeforeEach
	public void setUp() {
		this.calculator = new Calculator();
		this.numFilepath = getClass().getResource("numbers.txt").getPath();
	}
	
	
	@Test
	public void sumOfNumbers() throws IOException {
		assertEquals(10, calculator.calcSum(numFilepath));
	}

	@Test
	public void multiplyOfNumbers() throws IOException {
		assertEquals(24, calculator.calcMultiply(numFilepath));
	}
}
