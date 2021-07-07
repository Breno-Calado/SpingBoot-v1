package com.brenomaia.cusromc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brenomaia.cusromc.domain.ItemPedido;
import com.brenomaia.cusromc.domain.Pagamento;
import com.brenomaia.cusromc.domain.PagamentoComBoleto;
import com.brenomaia.cusromc.domain.Pedido;
import com.brenomaia.cusromc.domain.enums.EstadoPagamento;
import com.brenomaia.cusromc.repositories.ItemPedidoRepository;
import com.brenomaia.cusromc.repositories.PagamentoRepository;
import com.brenomaia.cusromc.repositories.PedidoRepository;
import com.brenomaia.cusromc.repositories.ProdutoRepository;
import com.brenomaia.cusromc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository pedidoDAO;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	
	public Pedido findByID(Integer id) {
		Optional<Pedido> findById = pedidoDAO.findById(id);
		return findById.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName())); 
		
	}
	
	public Pedido create(Pedido pedido) {
		
		pedido.setId(null);
		pedido.setInstante(new Date());
		
		pedido.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		pedido.getPagamento().setPedido(pedido);
		if (pedido.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) pedido.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, pedido.getInstante());
		}
		pedido = pedidoDAO.save(pedido);
		pagamentoRepository.save(pedido.getPagamento());
		for (ItemPedido ip : pedido.getItens()) {
			ip.setDesconto(0.0);
			ip.setPreco(produtoService.findByID(ip.getProduto().getId()).getPreco());
			ip.setPedido(pedido);
		}
		itemPedidoRepository.saveAll(pedido.getItens());
		
		return pedido;
	}

}
