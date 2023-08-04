package vol1.jhcode.ch7.learningtest.spring.embeddeddb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

public class EmbeddedDBTest {
	EmbeddedDatabase db;
	JdbcTemplate template;
	
	@BeforeEach
	public void setUp() {
		//== 내장형 DB를 생성하기 위한 코드 ==//
		db = new EmbeddedDatabaseBuilder()
				 .setType(H2)
				 .addScript("classpath:/vol1/jhcode/ch7/learningtest/spring/embeddeddb/schema.sql")
				 .addScript("classpath:/vol1/jhcode/ch7/learningtest/spring/embeddeddb/data.sql")
				 .build();
		
		template = new JdbcTemplate(db);
	}
	
	@AfterEach
	public void tearDown() {
		db.shutdown();
	}
	
	@Test
	public void initData() {
		List<Integer> result = template.query("select count(*) from sqlmap",
				(rs, rowNum) -> rs.getInt(1));
		
		int ret = (int) DataAccessUtils.singleResult(result);
		assertEquals(ret, 2);
		
		List<Map<String, Object>> list = template.queryForList("select * from sqlmap order by key_");
		assertEquals((String)list.get(0).get("key_"), "KEY1");
		assertEquals((String)list.get(0).get("sql_"), "SQL1");
		assertEquals((String)list.get(1).get("key_"), "KEY2");
		assertEquals((String)list.get(1).get("sql_"), "SQL2");
	}
	
	@Test
	public void insert() {
		template.update("insert into sqlmap(key_, sql_) values(?,?)", "KEY3", "SQL3");
		
		List<Integer> result = template.query("select count(*) from sqlmap", 
				(rs, rowNum) -> rs.getInt(1));
	    int ret = (int) DataAccessUtils.singleResult(result);
		assertEquals(ret, 3);
	}
}
