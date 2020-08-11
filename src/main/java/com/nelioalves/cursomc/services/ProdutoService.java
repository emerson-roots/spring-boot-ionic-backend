package com.nelioalves.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.nelioalves.cursomc.domain.Categoria;
import com.nelioalves.cursomc.domain.Produto;
import com.nelioalves.cursomc.repositories.CategoriaRepository;
import com.nelioalves.cursomc.repositories.ProdutoRepository;
import com.nelioalves.cursomc.services.exceptions.ObjectNotFoundExceptionEmerson;

//aula 50
@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository repo;
	
	@Autowired
	private CategoriaRepository categoriaRepository;

	public Produto find(Integer pId) {

		Optional<Produto> obj = repo.findById(pId);

		return obj.orElseThrow(() -> new ObjectNotFoundExceptionEmerson(
				"Objeto não encontrado! Id: " + pId + ", Tipo: " + Produto.class.getName()));
	}

	public Page<Produto> search(String nome, List<Integer> ids, Integer pPage, Integer pLinesPerPage, String pOrderBy,
			String pDirectionOrdenation) {

		// PageRequest do Spring Data
		PageRequest pageRequest = PageRequest.of(pPage, pLinesPerPage, Direction.valueOf(pDirectionOrdenation),
				pOrderBy);
		List<Categoria> categorias = categoriaRepository.findAllById(ids);
		return repo.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
	}

}
