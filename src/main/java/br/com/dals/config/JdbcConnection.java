package br.com.dals.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.omg.PortableServer.POA;

public abstract class JdbcConnection implements ConnectionConfig {

    protected Connection connection;
    private static String CLASS_NAME_DRIVE;
    private static final Logger LOOGER = Logger.getLogger(JdbcConnection.class);
    private static String URL_DATA_BASE;
    private static String USER;
    private static String PASSWORD;

    public JdbcConnection() {
	carregarProperties();
	this.connection = this.openConnection();
    }

    private void carregarProperties() {
	Properties properties = new Properties();
	try {
	    InputStream input = new FileInputStream("src/main/resources/dados-acesso.properties");
	    properties.load(input);
	    URL_DATA_BASE = properties.getProperty("URL_DATA_BASE");
	    USER = properties.getProperty("USER");
	    PASSWORD = properties.getProperty("PASSWORD");
	    CLASS_NAME_DRIVE = properties.getProperty("CLASS_NAME_DRIVE");
	} catch ( FileNotFoundException e ) {
	    LOOGER.error("Erro ao carregar arquivo de configuração "
	    	+ "de acesso ao banco de dados. ERROR: "+e.getMessage());
	} catch ( IOException e ) {
	    LOOGER.error("Erro ao carregar arquivo de configuração "
		    	+ "de acesso ao banco de dados. ERROR: "+e.getMessage());
	}
    }

    public Connection openConnection() {
	try {
	    Class.forName(CLASS_NAME_DRIVE);
	    return DriverManager.getConnection(URL_DATA_BASE, USER, PASSWORD);
	} catch ( SQLException e ) {
	    System.out.println("Problema na conexão com o banco.\nErro:" + e.getMessage());
	} catch ( ClassNotFoundException e ) {
	    System.out.println(e.getMessage());
	}
	return null;
    }

    public void closeConnection(Connection connection) {
	try {
	    connection.close();
	    System.out.println("Conexão fechada com sucesso.");
	} catch ( SQLException e ) {
	    System.out.println("Problemas no fechamento da conexão!.\nErro: " + e.getMessage());
	}
    }

}
