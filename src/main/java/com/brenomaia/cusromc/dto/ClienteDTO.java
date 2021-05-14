package com.brenomaia.cusromc.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.brenomaia.cusromc.domain.Cliente;

public class ClienteDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	@NotNull(message ="Preenchimento obrigatório" )
	@Length(min = 5, max = 120, message = "O tamanho deve ter entre 5 e 120 caracteres")
	private String name;
	
	@NotNull
	@Email
	private String email;
	
	public ClienteDTO() {
		
	}
	
	public ClienteDTO(Cliente clienteObj) {
		this.id = clienteObj.getId();
		this.name = clienteObj.getName();
		this.email = clienteObj.getEmail();
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	
	
	
	
}
