package com.brenomaia.cusromc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.brenomaia.cusromc.domain.Categoria;
import com.brenomaia.cusromc.domain.Produto;
import com.brenomaia.cusromc.repositories.CategoriaRepository;
import com.brenomaia.cusromc.repositories.ProdutoRepository;
import com.brenomaia.cusromc.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository produtoDAO;
	
	@Autowired
	private CategoriaRepository categoriaDAO;
	
	public Produto findByID(Integer id) {
		Optional<Produto> findById = produtoDAO.findById(id);
		return findById.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName())); 
	}
	
	
	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPage, Direction.valueOf(direction) , orderBy);
		List<Categoria> categorias = categoriaDAO.findAllById(ids);
		return produtoDAO.findDistinctByNameContainingAndCategoriasIn(nome, categorias, pageRequest );
		
		
	}

}
