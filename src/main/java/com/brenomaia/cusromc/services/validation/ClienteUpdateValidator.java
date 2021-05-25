package com.brenomaia.cusromc.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.brenomaia.cusromc.domain.Cliente;
import com.brenomaia.cusromc.domain.enums.TipoCliente;
import com.brenomaia.cusromc.dto.ClienteDTO;
import com.brenomaia.cusromc.repositories.ClienteRepository;
import com.brenomaia.cusromc.resources.exceptions.FieldMessage;
import com.brenomaia.cusromc.services.validation.utils.BR;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {
	
	@Autowired
	private HttpServletRequest resquest;
	
	@Autowired
	private ClienteRepository repo; 
	
	@Override
	public void initialize(ClienteUpdate ann) {
	}

	@Override
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {
		
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) resquest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriID = Integer.parseInt(map.get("id"));
		
		List<FieldMessage> list = new ArrayList<>();
		
		
		Cliente findByEmail = repo.findByEmail(objDto.getEmail());
		if(findByEmail != null && !findByEmail.getId().equals(uriID)) {
			list.add(new FieldMessage("email", "Email já existente"));
		}
		
		
		// inclua os testes aqui, inserindo erros na lista
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}

}
