package vol1.jhcode.ch7.user.slqservice;

public interface SqlReader {
	
	// SQL을 외부에서 가져와 SqlRegistry에 등록하여 사용한다
	void read(SqlRegistry sqlRegistry);

}
