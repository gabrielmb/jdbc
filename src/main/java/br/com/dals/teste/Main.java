package br.com.dals.teste;

import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import br.com.dals.dao.ContatoDao;
import br.com.dals.modelo.Contato;
import br.com.dals.modelo.Endereco;

public class Main {

    public static void main(String[] args) throws ParseException {

	ContatoDao contatoDAO = new ContatoDao();

	Endereco endecero = new Endereco("Rua Bari", 92, "Jardim Europa", "Uberlândia", "Brasil");

	// Inserindo contato novo
	Contato contato = new Contato();
	contato.setNome("Priscila Lourenço");
	contato.setEndereco(endecero);
	contato.setEmail("priscila.lo@gmail.com");
	contato.setDataNascimento(Date.valueOf(LocalDate.of(1990, Month.JULY, 10)));

	contatoDAO.adicionaContato(contato);
	MostraTodosOsContatos(contatoDAO.pesquisaTodosContatos());
	
	contato.setEmail("piscila.eikemiguel@gmail.com");
	contato.getEndereco().setLogradouro("Rua Alemanha");
	
	contatoDAO.atualizaContato(contato);
	
	MostraTodosOsContatos(contatoDAO.pesquisaTodosContatos());

    }

    private static void MostraTodosOsContatos(List<Contato> contatos) {
	System.out.println("Dados dos contatos:\n");
	contatos.forEach(contato -> {
	    System.out.println("ID: " + contato.getId());
	    System.out.println("Nome: " + contato.getNome());
	    System.out.println("E-mail: " + contato.getEmail());
	    System.out.println("Endereço: " + contato.getEndereco());
	    System.out.println("Data de Nascimento: " + contato.getDataNascimentoFormatada());
	    System.out.println();
	});
    }

}
