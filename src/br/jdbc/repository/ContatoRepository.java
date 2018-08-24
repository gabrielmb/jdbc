package br.jdbc.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import br.jdbc.dto.ContatoDTO;
import br.jdbc.dto.ContatoResponse;
import br.jdbc.model.Contato;
import br.jdbc.model.Endereco;

@Repository
public class ContatoRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ContatoResponse findAll ( ) {

	ContatoResponse result = new ContatoResponse( );

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
			    new BeanPropertyRowMapper< Endereco >( Endereco.class ) );

	    contatoDTO.setContato( c );
	    contatoDTO.setEndereco( endereco );

	    result.getContato( ).add( contatoDTO );
	} );

	return result;
    }

    public ContatoResponse save ( ContatoDTO contatoDTO ) {

	ContatoResponse result = new ContatoResponse( );
	
	final String sqlEndereco = "INSERT INTO endereco(logradouro, numero, bairro, cidade, pais) VALUES (?,?,?,?,?)";
	final String sqlContato = "INSERT INTO contato(nome, email, endereco, dataNascimento) VALUES (?,?,?,?)";
		 
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sqlEndereco, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, contatoDTO.getEndereco( ).getLogradouro( ));
                ps.setInt(2, contatoDTO.getEndereco( ).getNumero( ));
                ps.setString(3, contatoDTO.getEndereco( ).getBairro( ));
                ps.setString(4, contatoDTO.getEndereco( ).getCidade( ));
                ps.setString(5, contatoDTO.getEndereco( ).getPais( ));
                return ps;
            }
        }, holder);
 
        long enderecoId = holder.getKey().longValue( );
        contatoDTO.getEndereco( ).setId( enderecoId );
        
        holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sqlContato, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, contatoDTO.getContato( ).getNome( ));
                ps.setString(2, contatoDTO.getContato( ).getEmail( ));
                ps.setLong(3, contatoDTO.getContato( ).getEndereco( ));
                ps.setDate(4, new java.sql.Date(contatoDTO.getContato( ).getDataNascimento( ).getTime( )));
                return ps;
            }
        }, holder);
 
        long contatoId = holder.getKey().longValue( );
        contatoDTO.getContato( ).setId( contatoId );
        
        result.setContato( Arrays.asList( contatoDTO ) );
        
        return result;
    }

}
