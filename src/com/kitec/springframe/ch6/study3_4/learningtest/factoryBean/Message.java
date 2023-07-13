package com.kitec.springframe.ch6.study3_4.learningtest.factoryBean;


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
