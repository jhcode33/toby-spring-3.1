package com.jhcode.spring.ch6.user.service;

import javax.sql.DataSource;

import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import com.jhcode.spring.ch6.user.dao.UserDao;
import com.jhcode.spring.ch6.user.dao.UserDaoJdbc;

@Configuration
public class TestServiceFactory {
	
	@Bean
	public UserDao userDao() {
		UserDaoJdbc userDao = new UserDaoJdbc();
		userDao.setDataSource(dataSource());
		return userDao;
	}
	
	@Bean
	public DataSource dataSource() {
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		String url = "jdbc:mariadb://localhost:3306/toby_study?characterEncoding=UTF-8";
		String username = "root";
		String password = "1234";
		
		dataSource.setDriverClass(org.mariadb.jdbc.Driver.class);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		return dataSource;
	}
	
	@Bean
	public DataSourceTransactionManager transactionManager() {
		DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
		dataSourceTransactionManager.setDataSource(dataSource());
		return dataSourceTransactionManager;
	}
	
	@Bean
	public DummyMailSender mailSender() {
		DummyMailSender dummyMailSender = new DummyMailSender();
		return dummyMailSender;
	}
	
	@Bean
	public TransactionAdvice transactionAdvice() {
		TransactionAdvice advice = new TransactionAdvice();
		advice.setTransactionManager(transactionManager());
		return advice;
	}
	
	@Bean
	public NameMatchMethodPointcut transactionPointcut() {
		NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
		pointcut.setMappedName("upgrade*");
		return pointcut;
	}
	
	@Bean
	public DefaultPointcutAdvisor transactionAdvisor() {
		DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
		advisor.setAdvice(transactionAdvice());
		advisor.setPointcut(transactionPointcut());
		return advisor;
	}
	
	@Bean
	public ProxyFactoryBean userService() {
		ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
		proxyFactoryBean.setTarget(userServiceImpl());
		proxyFactoryBean.addAdvisor(transactionAdvisor());
		//proxyFactoryBean.setInterceptorNames("transactionAdvisor");
		return proxyFactoryBean;
	}
	
//	@Bean
//	public TxProxyFactoryBean userService() {
//		TxProxyFactoryBean txProxyFactorybean = new TxProxyFactoryBean();
//		txProxyFactorybean.setTarget(userServiceImpl());
//		txProxyFactorybean.setTransactionManager(transactionManager());
//		txProxyFactorybean.setPattern("upgradeLevels");
//		txProxyFactorybean.setServiceInterface(UserService.class);
//		return txProxyFactorybean;
//	}
	
	@Bean
	public UserService userServiceImpl() {
		UserServiceImpl userServiceImpl = new UserServiceImpl();
		userServiceImpl.setUserDao(userDao());
		userServiceImpl.setMailSender(mailSender());
		return userServiceImpl;
	}
	
	
}
