package com.nelioalves.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.nelioalves.cursomc.domain.Cliente;
import com.nelioalves.cursomc.dto.ClienteDTO;
import com.nelioalves.cursomc.repositories.ClienteRepository;
import com.nelioalves.cursomc.services.exceptions.DataIntegrityExceptionEmerson;
import com.nelioalves.cursomc.services.exceptions.ObjectNotFoundExceptionEmerson;

@Service // service é anotação do spring aula 17 13:00
public class ClienteService {

	// declara dependencia dentro de uma classe com AutoWired
	// o AutoWired automaticamente instancia a dependencia pelo mecanismo de injeção
	// de dependencia ou inversão de controle
	@Autowired
	private ClienteRepository repo;

	// aula 17 - 13:30 operação capaz de buscar uma CATEGORIA por código/id
	// essa operação, vai no banco de dados, busca uma categoria com esse ID
	// e ja retorna para mim o objeto pronto
	// feito isso, basta eu mandar o meu metodo buscar, retornar este objeto
	public Cliente find(Integer pId) {

		Optional<Cliente> obj = repo.findById(pId);

		// tratamento de excessao - caso faça busca no repositório e retorne nulo e
		// LANÇA EXCESSAO personalizada para a camada de recurso
		return obj.orElseThrow(() -> new ObjectNotFoundExceptionEmerson(
				"Objeto não encontrado! Id: " + pId + ", Tipo: " + Cliente.class.getName()));
	}

	// aula 35 - seção 3
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	// aula 36
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			// lembrar que é uma excessão personalizada
			throw new DataIntegrityExceptionEmerson("Não é possível excluir porque ha entidades relacionadas");
		}

	}

	public List<Cliente> findAll() {
		return repo.findAll();
	}

	public Page<Cliente> findPage(Integer pPage, Integer pLinesPerPage, String pOrderBy, String pDirectionOrdenation) {
		// PageRequest do Spring Data
		PageRequest pageRequest = PageRequest.of(pPage, pLinesPerPage, Direction.valueOf(pDirectionOrdenation),
				pOrderBy);
		return repo.findAll(pageRequest);
	}

	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
	}

	// aula 41
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}

}
