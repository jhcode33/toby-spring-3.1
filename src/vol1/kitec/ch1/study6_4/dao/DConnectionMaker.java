package vol1.kitec.ch1.study6_4.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DConnectionMaker implements ConnectionMaker {
	
	private String driverClass;
	private String url;
	private String username;
	private String password;
	
	public DConnectionMaker() {};
	
	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public Connection makeConnection() throws ClassNotFoundException, SQLException {
		Class.forName(driverClass);
		Connection c = DriverManager.getConnection(url, 
				username,
				password);
		return c;
	}

}
