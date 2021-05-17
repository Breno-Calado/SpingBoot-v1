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
import com.brenomaia.cusromc.domain.Cidade;
import com.brenomaia.cusromc.domain.Cliente;
import com.brenomaia.cusromc.domain.Endereco;
import com.brenomaia.cusromc.domain.enums.TipoCliente;
import com.brenomaia.cusromc.dto.CategoriaDTO;
import com.brenomaia.cusromc.dto.ClienteDTO;
import com.brenomaia.cusromc.dto.ClienteNewDTO;
import com.brenomaia.cusromc.repositories.ClienteRepository;
import com.brenomaia.cusromc.repositories.EnderecoRepository;
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
	
	@Autowired
	private EnderecoRepository enderecoDAO;
	
	public Cliente findById(Integer id) {
		Optional<Cliente> findById = clienteDAO.findById(id);
		return findById.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	
	public Cliente update(Cliente clienteObj) {
		Cliente cliente = findById(clienteObj.getId());
		updateDados(cliente, clienteObj);
		return clienteDAO.save(cliente);
	}

	public Cliente create(Cliente clienteObj) {
		clienteObj.setId(null);
		clienteDAO.save(clienteObj);
		enderecoDAO.saveAll(clienteObj.getEnderecos());
		
		return clienteObj;
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
	
	public Cliente fromDTO(ClienteNewDTO clienteNewObjDTO) {
		 Cliente cliente = new Cliente(null, clienteNewObjDTO.getName(), clienteNewObjDTO.getEmail(), clienteNewObjDTO.getCpfOuCnpj(), TipoCliente.toEnum(clienteNewObjDTO.getTipo()));
		 Cidade cidade = new Cidade(clienteNewObjDTO.getCidadeId(), null, null);
		 Endereco endereco = new Endereco(null, clienteNewObjDTO.getLogradouro(), clienteNewObjDTO.getNumero(), clienteNewObjDTO.getComplemento(), clienteNewObjDTO.getBairro(), clienteNewObjDTO.getCep(), cliente, cidade);
		 
		 cliente.getEnderecos().add(endereco);
		 cliente.getTelefones().add(clienteNewObjDTO.getTelefone01());
		 
		 if(clienteNewObjDTO.getTelefone02() != null) {
			 cliente.getTelefones().add(clienteNewObjDTO.getTelefone02());

		 }
		 if(clienteNewObjDTO.getTelefone03() != null) {
			 cliente.getTelefones().add(clienteNewObjDTO.getTelefone03());

		 }
		 
		 return cliente;
	}
}
