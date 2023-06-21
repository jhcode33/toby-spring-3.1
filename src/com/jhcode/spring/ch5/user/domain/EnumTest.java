package com.jhcode.spring.ch5.user.domain;

public class EnumTest {

	public static void main(String[] args) {
		Level level = Level.BASIC;
		int value = level.intValue();
		System.out.println(value);
	}

}
