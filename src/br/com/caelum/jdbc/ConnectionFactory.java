package br.com.caelum.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

	public Connection getConnection(){
		try {
			return DriverManager.getConnection("jdbc:mysql://localhost/fj21","root","root");
		} catch (Exception e) {
			System.out.println("Problema na conexão com o banco.\nErro:"+e.getMessage());
			return null;
		}
	}
	
	public void fechaConnection(Connection conexao){
		try{
			conexao.close();
			System.out.println("Conexão fechada com sucesso.");
		}catch(SQLException e){
			System.out.println("Problemas no fechamento da conexão!.\nErro: "+e.getMessage());
		}
	}
	
}
