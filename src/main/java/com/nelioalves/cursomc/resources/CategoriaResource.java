package com.nelioalves.cursomc.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.nelioalves.cursomc.domain.Categoria;
import com.nelioalves.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value = "/categorias") // aula 14
public class CategoriaResource {

	@Autowired
	private CategoriaService service;

	// aula 17 - endpoint - pela id da categoria
	// necessário o atributo value="/{id}"
	// com isto, a URL sera capaz de buscar uma categoria de acordo com
	// o endpoint id. Exemplo.: localhost/categorias/1
	// @PathVariable pega a variavel da URL {id} e passa para o parametro do metodo
	// find
	@RequestMapping(value = "/{id}", method = RequestMethod.GET) // aula 14
	public ResponseEntity<Categoria> find(@PathVariable Integer id) {

		// acessa serviço - que por sua vez, ira acessar
		// o objeto de acesso a dados que é o repository
		// implementando a lógica de camadas
		Categoria obj = service.find(id);

		// ResponseEntity.ok().body(obj) - diz q a operação ocorreu com sucesso e a
		// respota
		// tem como corpo o objeto categoria
		return ResponseEntity.ok().body(obj);

	}

	// aula 34
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody Categoria obj) {// @RequestBody faz o Json ser convertido para o
																	// objeto java automaticamente

		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();// URI
																														// do
																														// java.net

		return ResponseEntity.created(uri).build();

	}

	// aula 35 seção 3
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody Categoria obj, @PathVariable Integer id) {
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE) // aula 36
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
		
	}

}
