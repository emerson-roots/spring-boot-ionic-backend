package com.nelioalves.cursomc.resources;

import java.net.URI;

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

import com.nelioalves.cursomc.domain.Pedido;
import com.nelioalves.cursomc.services.PedidoService;

@RestController
@RequestMapping(value = "/pedidos") // aula 14
public class PedidoResource {

	@Autowired
	private PedidoService service;

	// aula 17 - endpoint - pela id da categoria
	// necessário o atributo value="/{id}"
	// com isto, a URL sera capaz de buscar uma categoria de acordo com
	// o endpoint id. Exemplo.: localhost/categorias/1
	// @PathVariable pega a variavel da URL {id} e passa para o parametro do metodo
	// find
	@RequestMapping(value = "/{id}", method = RequestMethod.GET) // aula 14
	public ResponseEntity<Pedido> find(@PathVariable Integer id) {

		// acessa serviço - que por sua vez, ira acessar
		// o objeto de acesso a dados que é o repository
		// implementando a lógica de camadas
		Pedido obj = service.find(id);

		// ResponseEntity.ok().body(obj) - diz q a operação ocorreu com sucesso e a
		// respota
		// tem como corpo o objeto categoria
		return ResponseEntity.ok().body(obj);

	}

	// aula 53
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody Pedido obj) {
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();

	}

	/**
	 * aula 76
	 * 
	 * na anotação @RequestMapping não foi utilizado o atributo "value" pois foi
	 * utilizado o endpoint principal "/pedidos"
	 */
	@RequestMapping(method = RequestMethod.GET) // aula 14
	public ResponseEntity<Page<Pedido>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer pPage,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer pLinesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "instante") String pOrderBy,
			@RequestParam(value = "direction", defaultValue = "DESC") String pDirectionOrdenation) {

		Page<Pedido> list = service.findPage(pPage, pLinesPerPage, pOrderBy, pDirectionOrdenation);
		return ResponseEntity.ok().body(list);

	}

}
