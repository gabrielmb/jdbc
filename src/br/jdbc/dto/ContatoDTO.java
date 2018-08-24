package br.jdbc.dto;

import br.jdbc.model.Contato;
import br.jdbc.model.Endereco;

public class ContatoDTO {

    private Contato contato;
    private Endereco endereco;
    
    public ContatoDTO() {}

    public ContatoDTO ( Contato contato, Endereco endereco) {

	this.contato = contato;
	this.endereco = endereco;
    }

    public Contato getContato ( ) {

	return contato;
    }

    public void setContato ( Contato contato ) {

	this.contato = contato;
    }

    public Endereco getEndereco ( ) {

	return endereco;
    }

    public void setEndereco ( Endereco endereco ) {

	this.endereco = endereco;
    }

}
