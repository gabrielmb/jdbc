package br.jdbc.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Contato {
    
    private Long id;
    private String nome;
    private String email;
    private Long endereco;
    private Date dataNascimento;

    public Contato ( ) {

    }

    public Contato ( Long id, String nome, String email, Long endereco, Date dataNascimento) {
	this.id = id;
	this.nome = nome;
	this.email = email;
	this.endereco = endereco;
	this.dataNascimento = dataNascimento;
    }


    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getNome() {
	return nome;
    }

    public void setNome(String nome) {
	this.nome = nome;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public Long getEndereco() {
	return endereco;
    }

    public void setEndereco(Long endereco) {
	this.endereco = endereco;
    }

    public Date getDataNascimento() {
	return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
	this.dataNascimento = dataNascimento;
    }
    
    public String getDataNascimentoFormatada() {
	if(dataNascimento != null)
	    return new SimpleDateFormat("dd/MM/yyyy").format(dataNascimento);
	
	return "";
    }

    @Override
    public String toString ( ) {

	return "Contato [id=" + id + ", nome=" + nome + ", email=" + email + ", endereco=" + endereco
			+ ", dataNascimento=" + dataNascimento + "]";
    }

}
