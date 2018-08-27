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
import org.springframework.transaction.annotation.Transactional;

import br.jdbc.dto.ContatoDTO;
import br.jdbc.dto.ContatoResponse;
import br.jdbc.exception.AddressNotInformationException;
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

    @Transactional
    public ContatoResponse save ( ContatoDTO contatoDTO ){

	ContatoResponse result = new ContatoResponse( );

	final String sqlEndereco = "INSERT INTO endereco(logradouro, numero, bairro, cidade, pais) VALUES (?,?,?,?,?)";
	final String sqlContato = "INSERT INTO contato(nome, email, endereco, dataNascimento) VALUES (?,?,?,?)";

	KeyHolder holder = new GeneratedKeyHolder( );
	jdbcTemplate.update( new PreparedStatementCreator( ) {

	    @Override
	    public PreparedStatement createPreparedStatement ( Connection connection ) throws SQLException {

		PreparedStatement ps = connection.prepareStatement( sqlEndereco, Statement.RETURN_GENERATED_KEYS );
		ps.setString( 1, contatoDTO.getEndereco( ).getLogradouro( ) );
		ps.setInt( 2, contatoDTO.getEndereco( ).getNumero( ) );
		ps.setString( 3, contatoDTO.getEndereco( ).getBairro( ) );
		ps.setString( 4, contatoDTO.getEndereco( ).getCidade( ) );
		ps.setString( 5, contatoDTO.getEndereco( ).getPais( ) );
		return ps;
	    }
	}, holder );

	long enderecoId = holder.getKey( ).longValue( );
	contatoDTO.getEndereco( ).setId( enderecoId );
	contatoDTO.getContato( ).setEndereco( enderecoId );

	holder = new GeneratedKeyHolder( );
	jdbcTemplate.update( new PreparedStatementCreator( ) {

	    @Override
	    public PreparedStatement createPreparedStatement ( Connection connection ) throws SQLException {

		PreparedStatement ps = connection.prepareStatement( sqlContato, Statement.RETURN_GENERATED_KEYS );
		ps.setString( 1, contatoDTO.getContato( ).getNome( ) );
		ps.setString( 2, contatoDTO.getContato( ).getEmail( ) );
		ps.setLong( 3, contatoDTO.getContato( ).getEndereco( ) );
		ps.setDate( 4, new java.sql.Date( contatoDTO.getContato( ).getDataNascimento( ).getTime( ) ) );
		return ps;
	    }
	}, holder );

	long contatoId = holder.getKey( ).longValue( );
	contatoDTO.getContato( ).setId( contatoId );

	result.setContato( Arrays.asList( contatoDTO ) );

	return result;
    }

    @Transactional
    public void delete ( Long contatoId ) {

	String sqlFindContato = "SELECT * FROM contato WHERE id = ?";

	String sqlDeleteContato = "DELETE FROM contato WHERE id = ?";
	String sqlDeleteEndereco = "DELETE FROM endereco WHERE id = ?";

	Contato contato = jdbcTemplate.queryForObject( sqlFindContato, new Object[] { contatoId.toString( ) },
			new BeanPropertyRowMapper<>( Contato.class ) );

	jdbcTemplate.update( sqlDeleteContato, new Object[] { contato.getId( ).toString( ) } );
	jdbcTemplate.update( sqlDeleteEndereco, new Object[] { contato.getEndereco( ).toString( ) } );

    }

    @Transactional
    public ContatoDTO update ( ContatoDTO contatoDTO ){

	String sqlFindContato = "SELECT * FROM contato WHERE id = ?";

	contatoDTO.setContato( jdbcTemplate.queryForObject( sqlFindContato,
			new Object[] { contatoDTO.getContato( ).getId( ).toString( ) },
			new BeanPropertyRowMapper<>( Contato.class ) ) );

	contatoDTO.getContato( ).setDataNascimento( contatoDTO.getContato( ).getDataNascimento( ) );
	contatoDTO.getContato( ).setEmail( contatoDTO.getContato( ).getEmail( ) );
	contatoDTO.getContato( ).setNome( contatoDTO.getContato( ).getNome( ) );

	if ( contatoDTO.getEndereco( ) != null && contatoDTO.getEndereco( ).getId( ) == null ) {
	    throw new AddressNotInformationException( );
	} else {

	    String sqlFindEndereo = "SELECT * FROM endereco WHERE id = ?";

	    contatoDTO.setEndereco( jdbcTemplate.queryForObject( sqlFindEndereo,
			    new Object[] { contatoDTO.getEndereco( ).getId( ).toString( ) },
			    new BeanPropertyRowMapper<>( Endereco.class ) ) );

	    contatoDTO.getEndereco( ).setBairro( contatoDTO.getEndereco( ).getBairro( ) );
	    contatoDTO.getEndereco( ).setCidade( contatoDTO.getEndereco( ).getCidade( ) );
	    contatoDTO.getEndereco( ).setLogradouro( contatoDTO.getEndereco( ).getLogradouro( ) );
	    contatoDTO.getEndereco( ).setNumero( contatoDTO.getEndereco( ).getNumero( ) );
	    contatoDTO.getEndereco( ).setPais( contatoDTO.getEndereco( ).getPais( ) );
	}

	String sqlUpdateContact = "UPDATE contato SET nome = ? , email = ? , dataNascimento = ? WHERE id = ?";
	String sqlUdpateAddress = "UPDATE endereco SET logradouro = ?, numero = ?, bairro = ?, cidade = ?, pais = ? WHERE id = ?";

	jdbcTemplate.update( new PreparedStatementCreator( ) {

	    @Override
	    public PreparedStatement createPreparedStatement ( Connection connection ) throws SQLException {

		PreparedStatement ps = connection.prepareStatement( sqlUpdateContact, Statement.RETURN_GENERATED_KEYS );
		ps.setString( 1, contatoDTO.getContato( ).getNome( ) );
		ps.setString( 2, contatoDTO.getContato( ).getEmail( ) );
		ps.setDate( 3, new java.sql.Date( contatoDTO.getContato( ).getDataNascimento( ).getTime( ) ) );
		ps.setLong( 4, contatoDTO.getContato( ).getId( ) );
		return ps;
	    }
	} );

	jdbcTemplate.update( new PreparedStatementCreator( ) {

	    @Override
	    public PreparedStatement createPreparedStatement ( Connection connection ) throws SQLException {

		// "logradouro = ?, numero = ?, bairro = ?, cidade = ?, pais = ? WHERE id = ?"
		PreparedStatement ps = connection.prepareStatement( sqlUdpateAddress, Statement.RETURN_GENERATED_KEYS );
		ps.setString( 1, contatoDTO.getEndereco( ).getLogradouro( ) );
		ps.setInt( 2, contatoDTO.getEndereco( ).getNumero( ) );
		ps.setString( 3, contatoDTO.getEndereco( ).getBairro( ) );
		ps.setString( 4, contatoDTO.getEndereco( ).getCidade( ) );
		ps.setString( 5, contatoDTO.getEndereco( ).getPais( ) );
		ps.setLong( 6, contatoDTO.getEndereco( ).getId( ) );
		return ps;
	    }
	} );

	return contatoDTO;
    }

}
