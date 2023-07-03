package com.kitec.springframe.ch1.study3_3.dao;

public class ConnectionFactory {
	
	//== connectionName을 판단해서 ConnectionMaker 구현체를 생성하고 반환하는 메소드 ==//
	public ConnectionMaker createConnectionMaker(String connectionName) {
		
		ConnectionMaker connectionMaker = null;
		
		if(connectionName.equals("D")) {
			connectionMaker = new DConnectionMaker();
		} else if(connectionName.equals("U")) {
			connectionMaker = new UConnectionMaker();
		}
		
		return connectionMaker;
	}

}
