package com.jhcode.spring.ch7.user.service;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class DummyMailSender implements MailSender {
	public void send(SimpleMailMessage mailMessage)
			throws MailException {}

	public void send(SimpleMailMessage[] mailMessage)
			throws MailException {}
	
}