package br.jdbc.repository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.jdbc.model.Endereco;

@Repository
public class EnderecoRepository {

    private static final Logger log = LoggerFactory.getLogger( EnderecoRepository.class );

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List< Endereco > findAll ( ) {

	return jdbcTemplate.query( "SELECT e FROM endereco e WHERE e.pais = ?", new Object[] { "BR" },
			( rs, rowNum ) -> new Endereco( rs.getLong( "id" ), rs.getString( "logradouro" ),
					rs.getInt( "numero" ), rs.getString( "bairro" ), rs.getString( "cidade" ),
					rs.getString( "pais" ) ) );
    }

    public void save ( Endereco endereco ) {

	jdbcTemplate.batchUpdate( "INSERT INTO endereco(logradouro, numero, bairro, cidade, pais) VALUES (?,?,?,?,?)",
			endereco.getLogradouro( ), endereco.getNumero( ).toString( ), endereco.getBairro( ),
			endereco.getCidade( ), endereco.getPais( ) );
	log.info( "Salvo com sucesso" );
    }

    public void delete ( Long idEndereco ) {

	jdbcTemplate.batchUpdate( "DELETE FROM endereco WHERE id = ?", idEndereco.toString( ) );
	log.info( "Endereço de código ["+idEndereco+"] apagado com sucesso" );
    }

}
