package br.com.dals.modelo;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class Contato {

    private Long id;
    private String nome;
    private String email;
    private Endereco endereco;
    private Date dataNascimento;

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

    public Endereco getEndereco() {
	return endereco;
    }

    public void setEndereco(Endereco endereco) {
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
    public String toString() {
	return "Contato [id=" + id + ", nome=" + nome + ", email=" + email + ", " + endereco.toString()
	        + ", dataNascimento=" + dataNascimento + "]";
    }
    
    

}
