package com.jhcode.spring.ch6.user.service;

import java.lang.reflect.Proxy;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

public class TxProxyFactoryBean implements FactoryBean<Object> {

	//TransactionHandler를 생성할 때 필요
	Object target;
	PlatformTransactionManager transactionManager;
	String pattern;
	
	//프록시가 구현할 인터페이스의 Class
	Class<?> serviceInterface;
	
	public void setTarget(Object target) {
		this.target = target;
	}
	
	public void setTransactionManager(
			PlatformTransactionManager transactionManger) {
		this.transactionManager = transactionManger;
	}
	
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	
	public void setServiceInterface(Class<?> serviecInterface) {
		this.serviceInterface = serviecInterface;
	}
	
	//FactoryBean 인터페이스의 정의된 getObject() 메소드 구현
	@Override
	public Object getObject() throws Exception {
		TransactionHandler txHandler = new TransactionHandler();
		txHandler.setTarget(target);
		txHandler.setTransactionManager(transactionManager);
		txHandler.setPattern(pattern);
		return Proxy.newProxyInstance(getClass().getClassLoader(),
									  new Class[] { serviceInterface },
									  txHandler);
	}

	@Override
	public Class<?> getObjectType() {
		return serviceInterface;
	}

	@Override
	public boolean isSingleton() {
		//싱글톤 빈이 아니라는 의미가 아니라, getObject()가 매번 같은 오브젝트를 리턴하지 않는다?
		//-> 결국 getObject() 메소드를 컨테이너가 사용해서 내부적으로 프록시 객체를 반환하는데
		//-> 매번 같은 오브젝트를 리턴하지 않으면 싱글톤이 아닌게 아닌가?
		return false;
	}
}
