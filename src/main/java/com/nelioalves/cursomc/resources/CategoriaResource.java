package com.nelioalves.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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

import com.nelioalves.cursomc.domain.Categoria;
import com.nelioalves.cursomc.dto.CategoriaDTO;
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
	// @RequestBody faz o Json ser convertido para o objeto java automaticamente
	//anotação @Valid da aula 39 - usada para validar objetos DTO
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO objDto) {
		
		Categoria obj = service.fromDTO(objDto);
		obj = service.insert(obj);
		// URI do java.net
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();

		return ResponseEntity.created(uri).build();

	}

	// aula 35 seção 3
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody CategoriaDTO objDto, @PathVariable Integer id) {
		
		Categoria obj = service.fromDTO(objDto);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}

	// aula 36
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE) // aula 36
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();

	}

	// aula 37
	@RequestMapping(method = RequestMethod.GET) // aula 14
	public ResponseEntity<List<CategoriaDTO>> findAll() {

		List<Categoria> list = service.findAll();
		// stream() - recurso do java 8 para percorrer listas
		List<CategoriaDTO> listDto = list.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);

	}

	// aula 38
	@RequestMapping(value = "/page", method = RequestMethod.GET) // aula 14
	public ResponseEntity<Page<CategoriaDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer pPage,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer pLinesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String pOrderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String pDirectionOrdenation) {

		Page<Categoria> list = service.findPage(pPage, pLinesPerPage, pOrderBy, pDirectionOrdenation);
		Page<CategoriaDTO> listDto = list.map(obj -> new CategoriaDTO(obj));
		return ResponseEntity.ok().body(listDto);

	}

}
