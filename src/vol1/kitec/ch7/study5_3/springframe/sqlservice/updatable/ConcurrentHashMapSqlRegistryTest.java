package vol1.kitec.ch7.study5_3.springframe.sqlservice.updatable;

import vol1.kitec.ch7.study5_3.springframe.sqlservice.UpdatableSqlRegistry;

public class ConcurrentHashMapSqlRegistryTest extends AbstractUpdatableSqlRegistryTest {
	protected UpdatableSqlRegistry createUpdatableSqlRegistry() {
		return new ConcurrentHashMapSqlRegistry();
	}
}