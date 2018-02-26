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
	StringBuilder builderScriptAdicionaEndereco = new StringBuilder();
	StringBuilder builderScriptAdicionaContato = new StringBuilder();

	builderScriptAdicionaEndereco.append("insert into tb_endereco (logradouro,numero,bairro,cidade,pais) ");
	builderScriptAdicionaEndereco.append(" values (?,?,?,?,?) ");

	builderScriptAdicionaContato.append("insert into tb_contato (id_endereco,nome,email,data_nascimento) ");
	builderScriptAdicionaContato.append(" values (?,?,?,?) ");

	try {
	    PreparedStatement statementEndereco = connection.prepareStatement(builderScriptAdicionaEndereco.toString(),
	            Statement.RETURN_GENERATED_KEYS);

	    connection.setAutoCommit(false);
	    
	    statementEndereco.setString(1, contato.getEndereco().getLogradouro());
	    statementEndereco.setInt(2, contato.getEndereco().getNumero());
	    statementEndereco.setString(3, contato.getEndereco().getBairro());
	    statementEndereco.setString(4, contato.getEndereco().getCidade());
	    statementEndereco.setString(5, contato.getEndereco().getPais());

	    statementEndereco.executeUpdate();
	    ResultSet resultEndereco = statementEndereco.getGeneratedKeys();
	    if ( resultEndereco.next() ) {
		contato.getEndereco().setId(resultEndereco.getLong(1));
	    }

	    PreparedStatement statementContato = connection.prepareStatement(builderScriptAdicionaContato.toString(),
		    Statement.RETURN_GENERATED_KEYS);

	    statementContato.setLong(1, contato.getEndereco().getId());
	    statementContato.setString(2, contato.getNome());
	    statementContato.setString(3, contato.getEmail());
	    statementContato.setDate(4, contato.getDataNascimento());

	    statementContato.executeUpdate();
	    ResultSet resultContato = statementContato.getGeneratedKeys();
	    if ( resultContato.next() ) {
		contato.setId(resultContato.getLong(1));
	    }

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
	
	StringBuilder builderScriptBuscaContato = new StringBuilder(" select * from tb_contato ");
	
	try {
	    PreparedStatement statement = connection.prepareStatement(builderScriptBuscaContato.toString());
	    
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
	    StringBuilder builderScriptBuscaEndereco = new StringBuilder();
	    builderScriptBuscaEndereco.append(" select * from tb_endereco where id = ? ");
	    PreparedStatement statement = connection.prepareStatement(builderScriptBuscaEndereco.toString());
	    
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

    public void atualizaContato(Contato contato) {
	try {
	    StringBuilder scriptAtualizaContato = new StringBuilder();
	    StringBuilder scriptAtualizaEndereco = new StringBuilder();
	    
	    scriptAtualizaEndereco.append("update tb_endereco set logradouro = ?, numero = ?, bairro = ?, ");
	    scriptAtualizaEndereco.append(" cidade = ?, pais = ? where id = ? ");
	    
	    scriptAtualizaContato.append("update tb_contato set nome = ?, email = ?, data_nascimento = ? where id = ? ");
	    
	    connection.setAutoCommit(false);
	    
	    PreparedStatement statementEndereco = connection.prepareStatement(scriptAtualizaEndereco.toString());
	    
	    statementEndereco.setString(1, contato.getEndereco().getLogradouro());
	    statementEndereco.setInt(2, contato.getEndereco().getNumero());
	    statementEndereco.setString(3, contato.getEndereco().getBairro());
	    statementEndereco.setString(4, contato.getEndereco().getCidade());
	    statementEndereco.setString(5, contato.getEndereco().getPais());
	    statementEndereco.setLong(6, contato.getEndereco().getId());
	    
	    statementEndereco.executeUpdate();
	    
	    PreparedStatement statementContato = connection.prepareStatement(scriptAtualizaContato.toString());
	    
	    statementContato.setString(1, contato.getNome());
	    statementContato.setString(2, contato.getEmail());
	    statementContato.setDate(3, contato.getDataNascimento());
	    statementContato.setLong(4, contato.getId());
	    
	    statementContato.executeUpdate();
	    
	    connection.commit();
	    
	} catch ( SQLException e ) {
	    LOOGER.error("Erro ContatoDao atualizaContato. ERROR: "+e.getMessage());
	    try {
		connection.rollback();
	    } catch ( SQLException e1 ) {
		LOOGER.error("Erro ContatoDao atualizaContato rollBack. ERROR: "+e1.getMessage());
	    }
	}
    }

}
