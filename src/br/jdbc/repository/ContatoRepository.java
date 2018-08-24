package br.jdbc.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.jdbc.dto.ContatoDTO;
import br.jdbc.model.Contato;
import br.jdbc.model.Endereco;

@Repository
public class ContatoRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List< ContatoDTO > findAll ( ) {

	List< ContatoDTO > result = new ArrayList<>( );

	String queryContato = "SELECT * FROM contato";
	String queryEndereco = "SELECT id, logradouro, numero, bairro, cidade, pais FROM endereco WHERE id = ?";

	List< Contato > contatos = jdbcTemplate.query( queryContato,
			( rs, rowNum ) -> new Contato( rs.getLong( "id" ), rs.getString( "nome" ),
					rs.getString( "email" ), rs.getLong( "endereco" ),
					rs.getDate( "dataNascimento" ) ) );

	contatos.stream( ).forEach( c -> {
	    ContatoDTO contatoDTO = new ContatoDTO( );

	    Endereco endereco = (Endereco) jdbcTemplate.queryForObject( queryEndereco,
			    new Object[] { c.getEndereco( ).toString( ) },
			    new BeanPropertyRowMapper( Endereco.class ) );

	    contatoDTO.setContato( c );
	    contatoDTO.setEndereco( endereco );

	    result.add( contatoDTO );
	} );

	return result;
    }

}
