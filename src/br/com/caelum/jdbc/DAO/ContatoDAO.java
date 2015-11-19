package br.com.caelum.jdbc.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.caelum.jdbc.ConnectionFactory;
import br.com.caelum.jdbc.modelo.Contato;

public class ContatoDAO {

	Connection connection;
	
	public ContatoDAO(){
		this.connection = new ConnectionFactory().getConnection();
	}
	
	public void adicionaContato(Contato contato){
		
		String sql = "insert into contatos (nome,email,endereco,dataNascimento) values (?,?,?,?)";
		
		try {
			
			PreparedStatement stmt = connection.prepareStatement(sql);
			
			stmt.setString(1,contato.getNome()); 
			stmt.setString(2,contato.getEmail()); 
			stmt.setString(3,contato.getEndereco()); 
			stmt.setDate(4,contato.getDataNascimento());
			
			stmt.execute();
			stmt.close();
			
		} catch (SQLException e) {
			System.out.println("Problema no ContatoDAO.\nErro:"+e.getMessage());
		}
		
	}
	
	public void removeContato(Contato contato){
		
		String sql = "delete from contatos where contatos.id = ?";
		
		try {
			
			PreparedStatement stmt = connection.prepareStatement(sql);
			
			stmt.setLong(1, contato.getId());
			
			stmt.execute();
			stmt.close();
			
		} catch (SQLException e) {
			System.out.println("Erro ao remover contato.\n Erro: "+e.getMessage());
		}
	}
	
	public Contato pesquisaContatoById(int id){
		try{
			Contato contato = new Contato();
			String sql = "select * from contatos where contatos.id = ?";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1,id);
			
			ResultSet resultado = stmt.executeQuery();
			
			while(resultado.next()){
				contato.setId(resultado.getLong("id"));
				contato.setNome(resultado.getString("nome"));
				contato.setEmail(resultado.getString("email"));
				contato.setDataNascimento(resultado.getDate("dataNascimento"));
				contato.setEndereco(resultado.getString("endereco"));
			}
			
			stmt.close();
			return contato;
			
		}catch(SQLException e){
			System.out.println("Erro no pesquisar contato:\n Erro:"+e.getMessage());
			return null;
		}
		
	}

	public List<Contato> pesquisaAllContatos(){
		
		List<Contato> listaDeContatos = new ArrayList<Contato>();
		
		try{
			
			String sql = "select * from contatos";
			PreparedStatement stmt = connection.prepareStatement(sql);
			
			ResultSet resultado = stmt.executeQuery();
			
			while(resultado.next()){
				Contato contato = new Contato();
				contato.setId(resultado.getLong("id"));
				contato.setNome(resultado.getString("nome"));
				contato.setEmail(resultado.getString("email"));
				contato.setDataNascimento(resultado.getDate("dataNascimento"));
				contato.setEndereco(resultado.getString("endereco"));
				listaDeContatos.add(contato);
			}
			
			stmt.close();
			return listaDeContatos;
			
		}catch(SQLException e){
			System.out.println("Erro no pesquisar contato:\n Erro:"+e.getMessage());
			return null;
		}
	}
	
	public void atualizaContato(Contato contato){
		
		String sql = "update contatos set nome = ? , email = ?, endereco = ? ,dataNascimento = ? where id=3";
		
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			
			stmt.setString(1,contato.getNome());
			stmt.setString(2, contato.getEmail());
			stmt.setString(3, contato.getEndereco());
			stmt.setDate(4, contato.getDataNascimento());
			stmt.setLong(5, contato.getId());
			
			stmt.execute();
			stmt.close();
			
		} catch (SQLException e) {
			System.out.println("Problemas com atualizaContatos.\n Erro:"+e.getMessage());
		}
		
	}
}
