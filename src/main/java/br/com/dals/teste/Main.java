package br.com.dals.teste;

import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import br.com.dals.dao.ContatoDao;
import br.com.dals.modelo.Contato;

public class Main {

public static void main(String[] args) throws ParseException {
		
		ContatoDao contatoDAO = new ContatoDao();
		
		//Inserindo contato novo
		Contato novo = new Contato();
		novo.setNome("Joao da Silva 3");
		novo.setEndereco("Rua da sardinha 3");
		novo.setEmail("jao3@hotmail.com");
		novo.setDataNascimento(Date.valueOf(LocalDate.of(1989, Month.DECEMBER, 25)));
		
		contatoDAO.adicionaContato(novo);
		//Mostrando contato de numero 2
		//Contato contato = contatoDAO.pesquisaContatoById(2);
		//MostraContao(contato);
		
		//Mostrando todos os contatos
		MostraTodosOsContatos(contatoDAO.pesquisaAllContatos());
		
	}

	private static void MostraTodosOsContatos(List<Contato> contatos) {
		
		System.out.println("Dados dos contatos:\n");
		for(Contato contato : contatos){
			System.out.println("ID: "+contato.getId());
			System.out.println("Nome: "+contato.getNome());
			System.out.println("E-mail: "+contato.getEmail());
			System.out.println("Endere�o: "+contato.getEndereco());
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
			System.out.println("Endere�o: "+contato.getEndereco());
			System.out.println("Data de Nascimento: "+(Date)contato.getDataNascimento());
			
		}else{
			System.out.println("N�o existe contato");
		}
		
	}
}
