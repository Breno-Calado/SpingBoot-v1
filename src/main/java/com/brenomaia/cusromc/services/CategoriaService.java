package com.brenomaia.cusromc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brenomaia.cusromc.domain.Categoria;
import com.brenomaia.cusromc.repositories.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository categoriaDAO;
	
	public Categoria findByID(Integer id) {
		Optional<Categoria> findById = categoriaDAO.findById(id);
		return findById.orElse(null);
		
	}

}
