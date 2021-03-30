/**
 * 
 */
package com.brenomaia.cusromc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.brenomaia.cusromc.domain.Cliente;
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
	
	
}
