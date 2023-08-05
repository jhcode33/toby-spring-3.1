package vol1.jhcode.ch7.user.service;

import javax.sql.DataSource;

import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import vol1.jhcode.ch7.user.dao.UserDao;
import vol1.jhcode.ch7.user.dao.UserDaoJdbc;
import vol1.jhcode.ch7.user.sqlservice.OxmSqlService;
import vol1.jhcode.ch7.user.sqlservice.updatable.EmbeddedDbSqlRegistry;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "ch7")
public class TestServiceFactory {
	@Bean
	public DataSource dataSource() {
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		String url = "jdbc:mariadb://localhost:3306/testdb?characterEncoding=UTF-8";
		String username = "root";
		String password = "1234";
		
		dataSource.setDriverClass(org.mariadb.jdbc.Driver.class);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		return dataSource;
	}
	//== aop ==//
	@Bean
	public DataSourceTransactionManager transactionManager() {
		DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
		dataSourceTransactionManager.setDataSource(dataSource());
		return dataSourceTransactionManager;
	}
	
	//== sqlSerivce ==//
	@Bean
	public OxmSqlService sqlService() {
		OxmSqlService oxmSqlService = new OxmSqlService();
		oxmSqlService.setSqlmap(new ClassPathResource
				("/vol1/jhcode/ch7/user/dao/sqlmap.xml", UserDao.class));
		oxmSqlService.setUnmarshaller(unmarshaller());
		oxmSqlService.setSqlRegistry(sqlRegistry());
		return oxmSqlService;
	}
	
	@Bean
	public EmbeddedDbSqlRegistry sqlRegistry() {
		EmbeddedDbSqlRegistry embeddedDbSqlRegistry = new EmbeddedDbSqlRegistry();
		embeddedDbSqlRegistry.setDataSource(embeddedDatabase());
		return embeddedDbSqlRegistry;
	}
	
	@Bean
    public DataSource embeddedDatabase() {
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
        databasePopulator.addScript(new ClassPathResource("vol1/jhcode/ch7/user/sqlservice/updatable/sqlRegistrySchema.sql"));

        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .setName("embeddedDatabase")
                .setScriptEncoding("UTF-8")
                .addScript("classpath:vol1/jhcode/ch7/user/sqlservice/updatable/sqlRegistrySchema.sql")
                .build();
    }
	
	@Bean
	public Jaxb2Marshaller unmarshaller() {
		Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
		jaxb2Marshaller.setContextPath("vol1.jhcode.ch7.user.sqlservice.jaxb");
		return jaxb2Marshaller;
	}
	
	// application components
	@Bean
	public UserDao userDao() {
		UserDaoJdbc userDao = new UserDaoJdbc();
		userDao.setDataSource(dataSource());
		userDao.setSqlService(sqlService());
		return userDao;
	}
	
	@Bean
	public UserService userService() {
		UserServiceImpl userServiceImpl = new UserServiceImpl();
		return userServiceImpl;
	}
	
	@Bean
	public UserService userServiceImpl() {
		UserServiceImpl userServiceImpl = new UserServiceImpl();
		return userServiceImpl;
	}
	
	@Bean
	public UserService testUserService() {
		TestUserServiceImpl testuserServiceImpl = new TestUserServiceImpl();
		return testuserServiceImpl;
	}
	
	@Bean
	public DummyMailSender mailSender() {
		DummyMailSender dummyMailSender = new DummyMailSender();
		return dummyMailSender;
	}
}
