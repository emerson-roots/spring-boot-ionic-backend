package com.nelioalves.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.nelioalves.cursomc.domain.Categoria;
import com.nelioalves.cursomc.repositories.CategoriaRepository;
import com.nelioalves.cursomc.services.exceptions.DataIntegrityExceptionEmerson;
import com.nelioalves.cursomc.services.exceptions.ObjectNotFoundExceptionEmerson;

@Service // service é anotação do spring aula 17 13:00
public class CategoriaService {

	// declara dependencia dentro de uma classe com AutoWired
	// o AutoWired automaticamente instancia a dependencia pelo mecanismo de injeção
	// de dependencia ou inversão de controle
	@Autowired
	private CategoriaRepository repo;

	// aula 17 - 13:30 operação capaz de buscar uma CATEGORIA por código/id
	// essa operação, vai no banco de dados, busca uma categoria com esse ID
	// e ja retorna para mim o objeto pronto
	// feito isso, basta eu mandar o meu metodo buscar, retornar este objeto
	public Categoria find(Integer pId) {

		Optional<Categoria> obj = repo.findById(pId);

		// tratamento de excessao - caso faça busca no repositório e retorne nulo e
		// LANÇA EXCESSAO personalizada para a camada de recurso
		return obj.orElseThrow(() -> new ObjectNotFoundExceptionEmerson(
				"Objeto não encontrado! Id: " + pId + ", Tipo: " + Categoria.class.getName()));
	}

	// aula 34
	public Categoria insert(Categoria obj) {
		obj.setId(null);// garante que o novo objeto a ser inserido tem id nulo, caso contrario entende
						// como uma atualização
		return repo.save(obj);
	}

	// aula 35 - seção 3
	public Categoria update(Categoria obj) {
		// chama o metodo de busca para verificar se o id existe
		find(obj.getId());
		return repo.save(obj);
	}

	// aula 36
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityExceptionEmerson("Não é possível excluir uma categoria que possui produtos");//lembrar que é uma excessão personalizada
		}

	}
	
	//aula 37
	public List<Categoria> findAll(){
		return repo.findAll();
	}
	
	//aula 38
	public Page<Categoria> findPage(Integer pPage, Integer pLinesPerPage, String pOrderBy, String pDirectionOrdenation){
		//PageRequest do Spring Data
		PageRequest pageRequest =  PageRequest.of(pPage, pLinesPerPage, Direction.valueOf(pDirectionOrdenation), pOrderBy);
		return repo.findAll(pageRequest);
	}
	
}
