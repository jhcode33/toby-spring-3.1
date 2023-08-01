package vol1.jhcode.ch3.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface StatementStrategy {
	
	//== 전략패턴에서 인터페이스로 사용할 공통된 기능인, PrerparedStatement 객체 생성 메소드 ==//
	PreparedStatement makePreparedStatement(Connection con) throws SQLException;

}
