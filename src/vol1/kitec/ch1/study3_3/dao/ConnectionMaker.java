package vol1.kitec.ch1.study3_3.dao;

import java.sql.Connection;
import java.sql.SQLException;

//== product ==//
public interface ConnectionMaker {
	
	public abstract Connection makeConnection() throws ClassNotFoundException,
	SQLException;

}
