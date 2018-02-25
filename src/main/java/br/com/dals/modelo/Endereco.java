package br.com.dals.modelo;

public class Endereco {

    private Long id;
    private String logradouro;
    private Integer numero;
    private String bairro;
    private String cidade;
    private String pais;
    
    public Endereco() {
    }

    public Endereco(String logradouro, Integer numero, String bairro, String cidade, String pais) {
	this.logradouro = logradouro;
	this.numero = numero;
	this.bairro = bairro;
	this.cidade = cidade;
	this.pais = pais;
    }

    public Long getId() {
	return id;
    }

    public String getLogradouro() {
	return logradouro;
    }

    public Integer getNumero() {
	return numero;
    }

    public String getBairro() {
	return bairro;
    }

    public String getCidade() {
	return cidade;
    }

    public String getPais() {
	return pais;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public void setLogradouro(String logradouro) {
	this.logradouro = logradouro;
    }

    public void setNumero(Integer numero) {
	this.numero = numero;
    }

    public void setBairro(String bairro) {
	this.bairro = bairro;
    }

    public void setCidade(String cidade) {
	this.cidade = cidade;
    }

    public void setPais(String pais) {
	this.pais = pais;
    }

    @Override
    public String toString() {
	return "Endereco [id=" + id + ", logradouro=" + logradouro + ", numero=" + numero + ", bairro=" + bairro
	        + ", cidade=" + cidade + ", pais=" + pais + "]";
    }

    
}
