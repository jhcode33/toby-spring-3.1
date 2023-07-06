package com.kitec.springframe.ch3.study4_1.springframe.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface StatementStrategy {
	PreparedStatement makePreparedStatement(Connection c) throws SQLException; 
}
