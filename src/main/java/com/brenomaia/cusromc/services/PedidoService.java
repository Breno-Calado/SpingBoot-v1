package com.brenomaia.cusromc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brenomaia.cusromc.domain.Pedido;
import com.brenomaia.cusromc.repositories.PedidoRepository;
import com.brenomaia.cusromc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository pedidoDAO;
	
	public Pedido findByID(Integer id) {
		Optional<Pedido> findById = pedidoDAO.findById(id);
		return findById.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName())); 
		
	}

}
