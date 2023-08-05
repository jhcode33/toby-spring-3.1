package vol1.jhcode.ch7;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import vol1.jhcode.ch7.user.sqlservice.OxmSqlService;
import vol1.jhcode.ch7.user.sqlservice.SqlRegistry;
import vol1.jhcode.ch7.user.sqlservice.SqlService;
import vol1.jhcode.ch7.user.sqlservice.updatable.EmbeddedDbSqlRegistry;

@Configuration
public class SqlServiceContext {
	
	@Bean
	public SqlService sqlService() {
		OxmSqlService sqlService = new OxmSqlService();
		sqlService.setUnmarshaller(unmarshaller());
		sqlService.setSqlRegistry(sqlRegistry());
		return sqlService;
	}
	
	@Bean
	public SqlRegistry sqlRegistry() {
		EmbeddedDbSqlRegistry sqlRegistry = new EmbeddedDbSqlRegistry();
		sqlRegistry.setDataSource(embeddedDatabase());
		return sqlRegistry;
	}
	
	@Bean
	public Unmarshaller unmarshaller() {
		Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
		jaxb2Marshaller.setContextPath("vol1.jhcode.ch7.user.sqlservice.jaxb");
		return jaxb2Marshaller;
	}
	
	@Bean
    public DataSource embeddedDatabase() {
        return new EmbeddedDatabaseBuilder()
        		.setName("embeddedDatabase")
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:vol1/jhcode/ch7/user/sqlservice/updatable/sqlRegistrySchema.sql")
                .build();
    }
}
