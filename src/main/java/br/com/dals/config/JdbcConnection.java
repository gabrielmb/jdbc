package br.com.dals.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class JdbcConnection implements ConnectionConfig{

	protected Connection connection;
	
	public JdbcConnection() {
		this.connection = this.openConnection();
	}
	
	public Connection openConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection("jdbc:mysql://localhost/db_agenda","root","root");
		} catch (SQLException e) {
			System.out.println("Problema na conexão com o banco.\nErro:"+e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public void closeConnection(Connection connection) {
		try{
			connection.close();
			System.out.println("Conexão fechada com sucesso.");
		}catch(SQLException e){
			System.out.println("Problemas no fechamento da conexão!.\nErro: "+e.getMessage());
		}
	}
	
}
