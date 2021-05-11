package com.brenomaia.cusromc.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.brenomaia.cusromc.domain.Categoria;
import com.brenomaia.cusromc.dto.CategoriaDTO;
import com.brenomaia.cusromc.services.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaService categoriaService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Categoria> findByID(@PathVariable Integer id) {

		Categoria findByID = categoriaService.findByID(id);

		return ResponseEntity.ok().body(findByID);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> create(@RequestBody Categoria categoriaObj) {
		Categoria categoriaCreate = categoriaService.create(categoriaObj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(categoriaCreate.getId())
				.toUri();
		return ResponseEntity.created(uri).build();

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> edit(@RequestBody Categoria categoriaObj, @PathVariable Integer id) {
		categoriaObj.setId(id);
		categoriaService.update(categoriaObj);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		categoriaService.delete(id);

		return ResponseEntity.noContent().build();
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> findAll() {
		List<CategoriaDTO> categorias = categoriaService.findAll();
		return ResponseEntity.ok().body(categorias);
	}
	
	@RequestMapping(value = "/page" , method = RequestMethod.GET)
	public ResponseEntity<Page<CategoriaDTO>> findPage(
			@RequestParam(value = "page" , defaultValue = "0") Integer page, 
			@RequestParam(value = "linesPerPage" , defaultValue = "24") Integer linesPerPage, 
			@RequestParam(value = "orderBy" , defaultValue = "name") String orderBy, 
			@RequestParam(value = "direction" , defaultValue = "ASC") String direction) {
		Page<Categoria> categorias = categoriaService.findPage(page, linesPerPage, orderBy, direction);
		
		Page<CategoriaDTO> listDTO = categorias.map(cat -> new CategoriaDTO(cat));
		return ResponseEntity.ok().body(listDTO);
	}

//	@RequestMapping(method = RequestMethod.GET)
//	public List<Categoria> list() {
//		
//		Categoria cat1 = new Categoria(1, "Informática");
//		Categoria cat2 = new Categoria(2, "Escritório");
//		Categoria cat3 = new Categoria(null, "Breno");
//		
//		List<Categoria> list = new ArrayList<>();
//		list.add(cat1);
//		list.add(cat2);
//		list.add(cat3);
//		return list; 
//	}

}
