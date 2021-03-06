package com.brenomaia.cusromc.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.brenomaia.cusromc.services.validation.ClienteInsert;

@ClienteInsert
public class ClienteNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message ="Preenchimento obrigatório" )
	@Length(max = 80, min = 5, message = "O tamanho deve ter entre 5 e 80 caracteres")
	private String name; 
	
	@NotNull
	@Email
	private String email;
	
	@NotNull
	private String cpfOuCnpj;
	private Integer tipo;
	
	@NotNull
	private String logradouro;
	
	@NotNull
	private String numero;
	
	private String complemento;
	
	private String bairro;
	
	@NotNull
	private String cep;
	
	@NotNull
	private String telefone01;
	
	private String telefone02;
	
	private String telefone03;
	
	
	private Integer cidadeId;
	
	
	
	public ClienteNewDTO() {
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}



	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}



	public Integer getTipo() {
		return tipo;
	}



	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}



	public String getLogradouro() {
		return logradouro;
	}



	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}



	public String getNumero() {
		return numero;
	}



	public void setNumero(String numero) {
		this.numero = numero;
	}



	public String getComplemento() {
		return complemento;
	}



	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}



	public String getBairro() {
		return bairro;
	}



	public void setBairro(String bairro) {
		this.bairro = bairro;
	}



	public String getCep() {
		return cep;
	}



	public void setCep(String cep) {
		this.cep = cep;
	}



	public String getTelefone01() {
		return telefone01;
	}



	public void setTelefone01(String telefone01) {
		this.telefone01 = telefone01;
	}



	public String getTelefone02() {
		return telefone02;
	}



	public void setTelefone02(String telefone02) {
		this.telefone02 = telefone02;
	}



	public String getTelefone03() {
		return telefone03;
	}



	public void setTelefone03(String telefone03) {
		this.telefone03 = telefone03;
	}



	public Integer getCidadeId() {
		return cidadeId;
	}



	public void setCidadeId(Integer cidadeId) {
		this.cidadeId = cidadeId;
	}

}
