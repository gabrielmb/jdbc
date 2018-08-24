package br.jdbc;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import br.jdbc.model.Endereco;

@SpringBootApplication( scanBasePackages = "br.jdbc" )
public class Application implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger( Application.class );

    @Autowired
    JdbcTemplate jdbcTemplate;

    public static void main ( String[] args ) {

	SpringApplication.run( Application.class, args );
    }

    @Override
    public void run ( String... args ) throws Exception {

	log.info( "Creating tables" );

	jdbcTemplate.execute( "DROP TABLE endereco IF EXISTS" );
	jdbcTemplate.execute( "CREATE TABLE endereco("
			+ "id SERIAL, logradouro VARCHAR(255), numero INTEGER, bairro VARCHAR(255), cidade VARCHAR(255), pais VARCHAR(3) )" );

	jdbcTemplate.execute( "DROP TABLE contato IF EXISTS" );
	jdbcTemplate.execute( "CREATE TABLE contato("
			+ "id SERIAL, nome VARCHAR(255), email VARCHAR(255), endereco LONG, dataNascimento DATE) " );

	List< Object[] > enderecos = Arrays.asList( "Rua Faz Sucesso-54-Nossa Alegria-Uberlândia-BR",
			"Rua Na Nave-12-Nossa Alegria-Uberlândia-BR", "Rua No Rio-432-Nossa Alegria-Uberlândia-BR",
			"Rua No Aviao-1927-Nossa Alegria-Uberlândia-BR",
			"Rua Faz Chuva-876-Nossa Alegria-Uberlândia-BR", "Rua Faz Neve-1-Nossa Alegria-Uberlândia-BR",
			"Rua Faz Nevasca-132-Nossa Alegria-Uberlândia-BR" ).stream( )
			.map( endereco -> endereco.split( "-" ) ).collect( Collectors.toList( ) );

	List< Object[] > contato = Arrays.asList( "Gabriel Morais barbosa=gabriel@hotmail.com=1=1989-12-14" ).stream( )
			.map( c -> c.split( "=" ) ).collect( Collectors.toList( ) );

	jdbcTemplate.batchUpdate( "INSERT INTO endereco(logradouro, numero, bairro, cidade, pais) VALUES (?,?,?,?,?)",
			enderecos );

	jdbcTemplate.batchUpdate( "INSERT INTO contato(nome, email, endereco, dataNascimento) VALUES (?,?,?,?)",
			contato );

	jdbcTemplate.query( "SELECT id, logradouro, numero, bairro, cidade, pais FROM endereco e WHERE e.pais = ?",
			new Object[] { "BR" },
			( rs, rowNum ) -> new Endereco( rs.getLong( "id" ), rs.getString( "logradouro" ),
					rs.getInt( "numero" ), rs.getString( "bairro" ), rs.getString( "cidade" ),
					rs.getString( "pais" ) ) )
			.forEach( end -> log.info( end.toString( ) ) );

    }

}
