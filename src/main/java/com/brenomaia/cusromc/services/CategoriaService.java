package com.brenomaia.cusromc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.brenomaia.cusromc.domain.Categoria;
import com.brenomaia.cusromc.repositories.CategoriaRepository;
import com.brenomaia.cusromc.services.exceptions.DataIntegrityException;
import com.brenomaia.cusromc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository categoriaDAO;
	
	public Categoria findByID(Integer id) {
		Optional<Categoria> findById = categoriaDAO.findById(id);
		return findById.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName())); 
		
	}
	
	public Categoria create(Categoria categoriaObj) {
		categoriaObj.setId(null);
		return categoriaDAO.save(categoriaObj);
	}
	
	public Categoria update(Categoria categoriaObj) {
		findByID(categoriaObj.getId());
		return categoriaDAO.save(categoriaObj);
	}
	
	public void delete(Integer id) {
		
		try {
			categoriaDAO.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
			
		}
	}

}
