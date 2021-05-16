/**
 * 
 */
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
import com.brenomaia.cusromc.domain.Cliente;
import com.brenomaia.cusromc.dto.CategoriaDTO;
import com.brenomaia.cusromc.dto.ClienteDTO;
import com.brenomaia.cusromc.repositories.ClienteRepository;
import com.brenomaia.cusromc.services.exceptions.DataIntegrityException;
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
	
	
	public Cliente update(Cliente clienteObj) {
		Cliente cliente = findById(clienteObj.getId());
		updateDados(cliente, clienteObj);
		return clienteDAO.save(cliente);
	}


	public void delete(Integer id) {

		try {
			clienteDAO.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma cliente que possui produtos");

		}
	}

	public List<ClienteDTO> findAll() {
		List<Cliente> findAll = clienteDAO.findAll();
		List<ClienteDTO> clienteDTO = findAll.stream().map(cat -> new ClienteDTO(cat)).collect(Collectors.toList());

		return clienteDTO;
	}

	
	public Page<Cliente> findPage(Integer page, Integer linesPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPage, Direction.valueOf(direction) , orderBy);
		return clienteDAO.findAll(pageRequest);
	}
	
	
	public Cliente fromDTO(ClienteDTO clienteDTO) {
		return new Cliente(clienteDTO.getId(), clienteDTO.getName(), clienteDTO.getEmail(), null, null);
	}
	
	private void updateDados(Cliente cliente, Cliente clienteObj) {
		cliente.setName(clienteObj.getName());
		cliente.setEmail(clienteObj.getEmail());
	}
	
}
