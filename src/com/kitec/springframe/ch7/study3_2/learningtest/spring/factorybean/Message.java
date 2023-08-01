package com.kitec.springframe.ch7.study3_2.learningtest.spring.factorybean;


public class Message {
String text;
	
	private Message(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}

	public static Message newMessage(String text) {
		return new Message(text);
	}

}
