package com.brenomaia.cusromc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.brenomaia.cusromc.domain.Categoria;
import com.brenomaia.cusromc.services.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService categoriaService;
	
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Categoria> findByID(@PathVariable Integer id){
		
		Categoria findByID = categoriaService.findByID(id);	
		
		return ResponseEntity.ok().body(findByID);
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
