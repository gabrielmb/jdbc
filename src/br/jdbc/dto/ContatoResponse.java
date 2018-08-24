package br.jdbc.dto;

import java.util.ArrayList;
import java.util.List;

public class ContatoResponse {

    List< ContatoDTO > contato;
    
    public ContatoResponse() {
	contato = new ArrayList<>();
    }

    public List< ContatoDTO > getContato ( ) {

	return contato;
    }

    public void setContato ( List< ContatoDTO > contato ) {

	this.contato = contato;
    }

    @Override
    public String toString ( ) {

	return "ContatoResponse [" + contato + "]";
    }
    
}
