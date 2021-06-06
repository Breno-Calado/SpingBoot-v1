package com.brenomaia.cusromc.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.brenomaia.cusromc.domain.Categoria;
import com.brenomaia.cusromc.domain.Produto;
import com.brenomaia.cusromc.dto.CategoriaDTO;
import com.brenomaia.cusromc.dto.ProdutoDTO;
import com.brenomaia.cusromc.resources.utils.URL;
import com.brenomaia.cusromc.services.ProdutoService;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {
	
	@Autowired
	private ProdutoService produtoService;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Produto> findById(@PathVariable Integer id){
		Produto findProduto = produtoService.findByID(id);
		return ResponseEntity.ok(findProduto);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> findPage(
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "categorias", defaultValue = "") String categorias,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		List<Integer> ids = URL.decodeIntList(categorias);
		String nameDecoded = URL.decodeParam(name);
		Page<Produto> list = produtoService.search(nameDecoded, ids, page, linesPerPage, orderBy, direction);

		Page<ProdutoDTO> listDTO = list.map(prod -> new ProdutoDTO(prod));
		return ResponseEntity.ok().body(listDTO);
	}
	
}
