package br.com.dals.config;

import java.sql.Connection;

public interface ConnectionConfig {

	public Connection openConnection();
	public void closeConnection(Connection connection);
	
}
