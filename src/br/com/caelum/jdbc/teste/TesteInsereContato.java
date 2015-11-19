package br.com.caelum.jdbc.teste;

import java.sql.Date;
import java.util.List;

import br.com.caelum.jdbc.DAO.ContatoDAO;
import br.com.caelum.jdbc.modelo.Contato;

public class TesteInsereContato {

	public static void main(String[] args) {
		
		ContatoDAO contatoDAO = new ContatoDAO();
		
		Contato contato = contatoDAO.pesquisaContatoById(2);
		
		MostraContao(contato);
		
		MostraTodosOsContatos(contatoDAO.pesquisaAllContatos());
		
	}

	private static void MostraTodosOsContatos(List<Contato> contatos) {
		
		System.out.println("Dados dos contatos:\n");
		for(Contato contato : contatos){
			System.out.println("ID: "+contato.getId());
			System.out.println("Nome: "+contato.getNome());
			System.out.println("E-mail: "+contato.getEmail());
			System.out.println("Endereço: "+contato.getEndereco());
			System.out.println("Data de Nascimento: "+(Date)contato.getDataNascimento());
			System.out.println();
		}
		
	}

	private static void MostraContao(Contato contato) {

		if(contato != null){
			
			System.out.println("Dados do contato:\n");
			System.out.println("ID: "+contato.getId());
			System.out.println("Nome: "+contato.getNome());
			System.out.println("E-mail: "+contato.getEmail());
			System.out.println("Endereço: "+contato.getEndereco());
			System.out.println("Data de Nascimento: "+(Date)contato.getDataNascimento());
			
		}else{
			System.out.println("Não existe contato");
		}
		
	}

}
