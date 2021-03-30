/**
 * 
 */
package com.brenomaia.cusromc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brenomaia.cusromc.domain.Cliente;
import com.brenomaia.cusromc.repositories.ClienteRepository;
import com.brenomaia.cusromc.services.exceptions.ObjectNotFoundException;

/**
 * @author breno
 *
 */

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteDAO;
	
	public Cliente findById(Integer id) {
		Optional<Cliente> findById = clienteDAO.findById(id);
		return findById.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
}
