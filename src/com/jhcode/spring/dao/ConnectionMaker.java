package com.jhcode.spring.dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionMaker {
	
	//== 인터페이스는 사용할 기능만 정의한다 ==//
	public Connection makeConnection() throws ClassNotFoundException, SQLException;
}
