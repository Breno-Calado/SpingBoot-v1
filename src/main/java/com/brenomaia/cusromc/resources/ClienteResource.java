/**
 * 
 */
package com.brenomaia.cusromc.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

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
import com.brenomaia.cusromc.domain.Cliente;
import com.brenomaia.cusromc.dto.CategoriaDTO;
import com.brenomaia.cusromc.dto.ClienteDTO;
import com.brenomaia.cusromc.dto.ClienteNewDTO;
import com.brenomaia.cusromc.services.ClienteService;

/**
 * @author breno
 *
 */
@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {

	@Autowired
	private ClienteService clienteService;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Cliente> findByID(@PathVariable Integer id){
		Cliente findById = clienteService.findById(id);
		
		return ResponseEntity.ok().body(findById);
	}
	
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> create(@Valid @RequestBody ClienteNewDTO clienteNewObjDTO) {
		Cliente clienteObj = clienteService.fromDTO(clienteNewObjDTO);

		Cliente categoriaCreate = clienteService.create(clienteObj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(categoriaCreate.getId())
				.toUri();
		return ResponseEntity.created(uri).build();

	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> edit(@Valid @RequestBody ClienteDTO categoriaObjDTO, @PathVariable Integer id) {
		Cliente categoriaObj = clienteService.fromDTO(categoriaObjDTO);

		categoriaObj.setId(id);
		clienteService.update(categoriaObj);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		clienteService.delete(id);

		return ResponseEntity.noContent().build();
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> findAll() {
		List<ClienteDTO> categorias = clienteService.findAll();
		return ResponseEntity.ok().body(categorias);
	}

	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		Page<Cliente> categorias = clienteService.findPage(page, linesPerPage, orderBy, direction);

		Page<ClienteDTO> listDTO = categorias.map(cat -> new ClienteDTO(cat));
		return ResponseEntity.ok().body(listDTO);
	}

	
}
