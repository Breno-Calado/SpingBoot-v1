package com.brenomaia.cusromc.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.brenomaia.cusromc.domain.Categoria;
import com.brenomaia.cusromc.dto.CategoriaDTO;
import com.brenomaia.cusromc.repositories.CategoriaRepository;
import com.brenomaia.cusromc.services.exceptions.DataIntegrityException;
import com.brenomaia.cusromc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaDAO;

	public Categoria findByID(Integer id) {
		Optional<Categoria> findById = categoriaDAO.findById(id);
		return findById.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));

	}

	public Categoria create(Categoria categoriaObj) {
		categoriaObj.setId(null);
		return categoriaDAO.save(categoriaObj);
	}

	public Categoria update(Categoria categoriaObj) {
		Categoria catDB = findByID(categoriaObj.getId());
		updataDados(catDB, categoriaObj );
		return categoriaDAO.save(catDB);
	}
	
	public void delete(Integer id) {

		try {
			categoriaDAO.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");

		}
	}

	public List<CategoriaDTO> findAll() {
		List<Categoria> findAll = categoriaDAO.findAll();
		List<CategoriaDTO> catDTO = findAll.stream().map(cat -> new CategoriaDTO(cat)).collect(Collectors.toList());

		return catDTO;
	}

	
	public Page<Categoria> findPage(Integer page, Integer linesPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPage, Direction.valueOf(direction) , orderBy);
		return categoriaDAO.findAll(pageRequest);
	}
	
	public Categoria fromDTO(CategoriaDTO categoriaDTO) {
		return new Categoria(categoriaDTO.getId(), categoriaDTO.getName());
	}
	
	public void updataDados(Categoria categoria, Categoria categoriaObj) {
		categoria.setName(categoriaObj.getName());
		
	}
}
