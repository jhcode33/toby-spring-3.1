package com.jhcode.spring.ch3.learningtest.template;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

public class CalcSumTest {
	
	@Test
	public void sumOfNumbers() throws IOException {
		
		Calculator calculator = new Calculator();
		int sum = calculator.calcSum(getClass().getResource("numbers.txt").getPath());
		
		assertEquals(10, sum);
	}

}
