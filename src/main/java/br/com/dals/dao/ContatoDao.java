package br.com.dals.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.dals.config.JdbcConnection;
import br.com.dals.modelo.Contato;
import br.com.dals.modelo.Endereco;

public class ContatoDao extends JdbcConnection {

    private static final Logger LOOGER = Logger.getLogger(ContatoDao.class);

    public ContatoDao() {
	super();
    }

    public void adicionaContato(Contato contato) {
	StringBuilder stringBuilderEndereco = new StringBuilder();
	StringBuilder stringBuilderContato = new StringBuilder();

	stringBuilderEndereco.append("insert into tb_endereco (logradouro,numero,bairro,cidade,pais) ");
	stringBuilderEndereco.append(" values (?,?,?,?,?) ");

	stringBuilderContato.append("insert into tb_contato (id_endereco,nome,email,data_nascimento) ");
	stringBuilderContato.append(" values (?,?,?,?) ");

	try {
	    PreparedStatement statementEndereco = connection.prepareStatement(stringBuilderEndereco.toString(),
	            Statement.RETURN_GENERATED_KEYS);

	    connection.setAutoCommit(false);
	    
	    statementEndereco.setString(1, contato.getEndereco().getLogradouro());
	    statementEndereco.setInt(2, contato.getEndereco().getNumero());
	    statementEndereco.setString(3, contato.getEndereco().getBairro());
	    statementEndereco.setString(4, contato.getEndereco().getCidade());
	    statementEndereco.setString(5, contato.getEndereco().getPais());

	    statementEndereco.executeUpdate();
	    ResultSet result = statementEndereco.getGeneratedKeys();
	    if ( result.next() ) {
		contato.getEndereco().setId(result.getLong(1));
	    }

	    PreparedStatement statementContato = connection.prepareStatement(stringBuilderContato.toString());

	    statementContato.setLong(1, contato.getEndereco().getId());
	    statementContato.setString(2, contato.getNome());
	    statementContato.setString(3, contato.getEmail());
	    statementContato.setDate(4, contato.getDataNascimento());

	    statementContato.execute();

	    connection.commit();
	} catch ( SQLException e ) {
	    LOOGER.error("Erro em ContatoDao AdicionaContato. ERROR: " + e.getMessage());
	    try {
		connection.rollback();
	    } catch ( SQLException e1 ) {
		LOOGER.error("Erro ContatoDao AdicionaContato rollback. ERROR: "+e1.getMessage());
	    }
	}

    }

    public List<Contato> pesquisaTodosContatos() {
	
	List<Contato> contatos = new ArrayList<>();
	
	StringBuilder builderFindContato = new StringBuilder(" select * from tb_contato ");
	
	try {
	    PreparedStatement statement = connection.prepareStatement(builderFindContato.toString());
	    
	    ResultSet result = statement.executeQuery();
	    
	    while(result.next()) {
		Contato contato = new Contato();
		contato.setId(result.getLong("id"));
		contato.setNome(result.getString("nome"));
		contato.setEmail(result.getString("email"));
		contato.setDataNascimento(result.getDate("data_nascimento"));
		contato.setEndereco(pesquisaEndereco(result.getLong("id_endereco")));
		contatos.add(contato);
	    }
	    
	} catch ( SQLException e ) {
	    LOOGER.error("Erro ContatoDao pesquisaAllContato. ERROR: "+e.getMessage());
	}

	return contatos;
    }

    private Endereco pesquisaEndereco(long idEndereco) {
	Endereco endereco = new Endereco();
	
	try {
	    StringBuilder builderFindEndecero = new StringBuilder();
	    builderFindEndecero.append(" select * from tb_endereco where id = ? ");
	    PreparedStatement statement = connection.prepareStatement(builderFindEndecero.toString());
	    
	    statement.setLong(1, idEndereco);
	    
	    ResultSet result = statement.executeQuery();
	    
	    if(result.next()) {
		endereco.setId(result.getLong("id"));
		endereco.setLogradouro(result.getString("logradouro"));
		endereco.setNumero(result.getInt("numero"));
		endereco.setBairro(result.getString("bairro"));
		endereco.setCidade(result.getString("cidade"));
		endereco.setPais(result.getString("pais"));
	    }
	    	    
	}catch (SQLException e) {
	    LOOGER.error("Erro ContatoDao pesquisaEndereco. ERROR: "+e.getMessage());
	}
	return endereco;
    }

}
