package com.brenomaia.cusromc.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.brenomaia.cusromc.domain.Categoria;

public class CategoriaDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message ="Preenchimento obrigatório" )
	@Length(max = 80, min = 5, message = "O tamanho deve ter entre 5 e 80 caracteres")
	private String name; 
	
	
	private Integer id;
	
	public CategoriaDTO() {
		
	}
	
	public CategoriaDTO(Categoria categoria) {
		id = categoria.getId();
		name = categoria.getName();
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	} 
	
	

}
