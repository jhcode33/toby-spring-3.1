package com.jhcode.spring.ch5.user.domain;

public enum Level {
	BASIC(1) , SILVER(2), GOLD(3);
	
	private final int value;
	
	//생성자 -> DB에 저장할 값인 필드를 초기화하는 역할
	Level(int value){
		this.value = value;
	}
	
	//필드 value의 값을 가져오는 메소드
	public int intValue() {
		return value;
	}
	
	//인수로 받은 값을 통해 각 상수 반환.
	public static Level valueOf(int value) {
		switch(value) {
		case 1: return BASIC;
		case 2: return SILVER;
		case 3: return GOLD;
		default: throw new AssertionError("Unknown value: " + value);
		}
	}
}
