package vol1.kitec.ch7.study5_1.springframe.sqlservice.updatable;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import vol1.kitec.ch7.study5_1.springframe.sqlservice.SqlNotFoundException;
import vol1.kitec.ch7.study5_1.springframe.sqlservice.SqlUpdateFailureException;
import vol1.kitec.ch7.study5_1.springframe.sqlservice.UpdatableSqlRegistry;

public class ConcurrentHashMapSqlRegistryTest {
	UpdatableSqlRegistry sqlRegistry;
	
	@BeforeEach
	public void setUp() {
		sqlRegistry = new ConcurrentHashMapSqlRegistry();
		sqlRegistry.registerSql("KEY1", "SQL1");
		sqlRegistry.registerSql("KEY2", "SQL2");
		sqlRegistry.registerSql("KEY3", "SQL3");
	}
	
	@Test
	public void find() {
		checkFindResult("SQL1", "SQL2", "SQL3");
	}

	private void checkFindResult(String expected1, String expected2, String expected3) {
		assertEquals(sqlRegistry.findSql("KEY1"), expected1);		
		assertEquals(sqlRegistry.findSql("KEY2"), expected2);		
		assertEquals(sqlRegistry.findSql("KEY3"), expected3);		
	}
	
	@Test
	public void unknownKey() {
		//sqlRegistry.findSql("SQL9999!@#$");
		Assertions.assertThrows(SqlNotFoundException.class, 
				() -> {sqlRegistry.findSql("SQL9999!@#$");});	
	}
			
	@Test
	public void updateSingle() {
		sqlRegistry.updateSql("KEY2", "Modified2");		
		checkFindResult("SQL1", "Modified2", "SQL3");
	}
	
	@Test
	public void updateMulti() {
		Map<String, String> sqlmap = new HashMap<String, String>();
		sqlmap.put("KEY1", "Modified1");
		sqlmap.put("KEY3", "Modified3");
		
		sqlRegistry.updateSql(sqlmap);		
		checkFindResult("Modified1", "SQL2", "Modified3");
	}

	@Test
	public void updateWithNotExistingKey() {
		//sqlRegistry.updateSql("SQL9999!@#$", "Modified2");
		Assertions.assertThrows(SqlUpdateFailureException.class, 
				() -> {sqlRegistry.updateSql("SQL9999!@#$", "Modified2");});	
	}

}
