package vol1.jhcode.ch7;

import javax.sql.DataSource;

import org.mariadb.jdbc.Driver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import vol1.jhcode.ch7.user.dao.UserDao;
import vol1.jhcode.ch7.user.service.DummyMailSender;
import vol1.jhcode.ch7.user.service.UserService;
import vol1.jhcode.ch7.user.service.UserServiceTest.TestUserService;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "vol1.jhcode.ch7.user")
@EnableSqlService
@PropertySource("classpath:/vol1/jhcode/ch7/database.properties")
public class AppContext implements SqlMapConfig {
	@Value("${db.driverClass}") Class<? extends Driver> driverClass;
	@Value("${db.url}") String url;
	@Value("${db.username}") String username;
	@Value("${db.password}") String password;
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	@Override
	public Resource getSqlMapResource() {
		return new ClassPathResource("sqlmap.xml", UserDao.class);
	}
	
	@Bean
	public DataSource dataSource() {
		SimpleDriverDataSource ds = new SimpleDriverDataSource();
		ds.setDriverClass(this.driverClass);
		ds.setUrl(this.url);
		ds.setUsername(this.username);
		ds.setPassword(this.password);
		return ds;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager() {
		DataSourceTransactionManager tm = new DataSourceTransactionManager();
		tm.setDataSource(dataSource());
		return tm;
	}
	
	@Configuration
	@Profile("production")
	public static class ProductionAppContext {
		@Bean
		public MailSender mailSender() {
			JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
			mailSender.setHost("localhost");
			return mailSender;
		}
	}
	
	@Configuration
	@Profile("test")
	public static class TestAppContext {
		@Bean
		public UserService testUserService() {
			return new TestUserService();
		}
		
		@Bean
		public MailSender mailSender() {
			return new DummyMailSender();
		}
	}
}
