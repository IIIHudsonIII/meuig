package com.br.meuig.usuario;

public class Usuario {

    private long id, glicemia_id;
    private String nome, dataNasc;
    private int altura;
    private double peso;
    private String sexo;

    public Usuario(long glicemia_id, String nome, String dataNasc, int altura,
	    double peso, String sexo) {
	this.glicemia_id = glicemia_id;
	this.nome = nome;
	this.dataNasc = dataNasc;
	this.altura = altura;
	this.peso = peso;
	this.sexo = sexo;
    }

    public Usuario() {

    }
    
    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public long getGlicemia_id() {
	return glicemia_id;
    }

    public void setGlicemia_id(long glicemia_id) {
	this.glicemia_id = glicemia_id;
    }

    public String getNome() {
	return nome;
    }

    public void setNome(String nome) {
	this.nome = nome;
    }

    public String getDataNasc() {
	return dataNasc;
    }

    public void setDataNasc(String dataNasc) {
	this.dataNasc = dataNasc;
    }

    public int getAltura() {
	return altura;
    }

    public void setAltura(int altura) {
	this.altura = altura;
    }

    public double getPeso() {
	return peso;
    }

    public void setPeso(double peso) {
	this.peso = peso;
    }

    public String getSexo() {
	return sexo;
    }

    public void setSexo(String sexo) {
	this.sexo = sexo;
    }

}
