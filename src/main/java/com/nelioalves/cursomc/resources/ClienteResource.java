package com.nelioalves.cursomc.resources;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nelioalves.cursomc.domain.Cliente;
import com.nelioalves.cursomc.services.ClienteService;

@RestController
@RequestMapping(value = "/clientes") // aula 14
public class ClienteResource {

	@Autowired
	private ClienteService service;

	// aula 17 - endpoint - pela id da categoria
	// necessário o atributo value="/{id}"
	// com isto, a URL sera capaz de buscar uma categoria de acordo com
	// o endpoint id. Exemplo.: localhost/categorias/1
	// @PathVariable pega a variavel da URL {id} e passa para o parametro do metodo
	// find
	@RequestMapping(value = "/{id}", method = RequestMethod.GET) // aula 14
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {

		// acessa serviço - que por sua vez, ira acessar
		// o objeto de acesso a dados que é o repository
		// implementando a lógica de camadas
		Cliente obj = service.find(id);
		
		//ResponseEntity.ok().body(obj) - diz q a operação ocorreu com sucesso e a respota
		//tem como corpo o objeto categoria
		return ResponseEntity.ok().body(obj);

	}
}
