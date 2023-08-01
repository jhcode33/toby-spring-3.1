package com.kitec.springframe.ch7.study3_2.learningtest.spring.factorybean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FactoryBeanConfig {
	
	@Bean
	public MessageFactoryBean message() {
		MessageFactoryBean messageFactoryBean = new MessageFactoryBean();
		messageFactoryBean.setText("Factory Bean");
		return messageFactoryBean;
	}

}
