package com.nelioalves.cursomc.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/categorias") // aula 14
public class CategoriaResource {
	
	@RequestMapping(method=RequestMethod.GET) // aula 14
	public String listar() {
		return "REST esta funcionando";
	}
}
