package com.jhcode.spring.ch7.user.dao;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import com.jhcode.spring.ch7.user.slqservice.SimpleSqlService;
import com.jhcode.spring.ch7.user.slqservice.SqlService;

@Configuration
public class TestDaoFactory {
	
	@Bean
	public DataSource dataSource() {
		
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		
		dataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class);
		dataSource.setUrl("jdbc:mysql://localhost:3306/testdb?characterEncoding=UTF-8");
		dataSource.setUsername("root");
		dataSource.setPassword("1234");

		return dataSource;
	}

	@Bean
	public UserDaoJdbc userDao() {
		UserDaoJdbc userDaoJdbc = new UserDaoJdbc();
		userDaoJdbc.setDataSource(dataSource());
		userDaoJdbc.setSqlService(sqlService()); // Map이 아닌 SqlService 주입
		return userDaoJdbc;
	}
	
	//== 여전히 SQL을 스프링 Bean에서 설정하지만 UserDao는 SQL이 어디에서 오는 건지 모르게 된다 ==//
	@Bean
    public SqlService sqlService() {
        SimpleSqlService sqlService = new SimpleSqlService();
		
		Map<String, String> sqlMap = new HashMap<>();
        sqlMap.put("add", "insert into users(id, name, password, email, level, login, recommend) values(?,?,?,?,?,?,?)");
        sqlMap.put("get", "select * from users where id = ?");
        sqlMap.put("getAll", "select * from users order by id");
        sqlMap.put("deleteAll", "delete from users");
        sqlMap.put("getCount", "select count(*) from users");
        sqlMap.put("update", "update users set name = ?, password = ?, email = ?, level = ?, login = ?, recommend = ? where id = ?");
        
        sqlService.setSqlMap(sqlMap);
        return sqlService;
    }
}


