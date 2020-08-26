package com.nelioalves.cursomc.services;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.nelioalves.cursomc.domain.Cidade;
import com.nelioalves.cursomc.domain.Cliente;
import com.nelioalves.cursomc.domain.Endereco;
import com.nelioalves.cursomc.domain.enums.Perfil;
import com.nelioalves.cursomc.domain.enums.TipoCliente;
import com.nelioalves.cursomc.dto.ClienteDTO;
import com.nelioalves.cursomc.dto.ClienteNewDTO;
import com.nelioalves.cursomc.repositories.ClienteRepository;
import com.nelioalves.cursomc.repositories.EnderecoRepository;
import com.nelioalves.cursomc.security.UserSS;
import com.nelioalves.cursomc.services.exceptions.AuthorizationExceptionEmerson;
import com.nelioalves.cursomc.services.exceptions.DataIntegrityExceptionEmerson;
import com.nelioalves.cursomc.services.exceptions.ObjectNotFoundExceptionEmerson;

@Service // service é anotação do spring aula 17 13:00
public class ClienteService {

	// declara dependencia dentro de uma classe com AutoWired
	// o AutoWired automaticamente instancia a dependencia pelo mecanismo de injeção
	// de dependencia ou inversão de controle
	@Autowired
	private ClienteRepository repo;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	// aula 86
	@Autowired
	private S3Service s3Service;

	// aula 17 - 13:30 operação capaz de buscar uma CATEGORIA por código/id
	// essa operação, vai no banco de dados, busca uma categoria com esse ID
	// e ja retorna para mim o objeto pronto
	// feito isso, basta eu mandar o meu metodo buscar, retornar este objeto
	public Cliente find(Integer pId) {

		/**
		 * ===============================
		 * 
		 * aula 75
		 * 
		 * verifica se o usuario q tentamos buscar for = null ou se nao tiver perfil de
		 * admin ou se o id for diferente do buscado
		 */
		UserSS user = UserService.authenticated();
		if (user == null || !user.hasRole(Perfil.ADMIN) && !pId.equals(user.getId())) {
			throw new AuthorizationExceptionEmerson("Acesso negado");
		}
		// ===============================

		Optional<Cliente> obj = repo.findById(pId);

		// tratamento de excessao - caso faça busca no repositório e retorne nulo e
		// LANÇA EXCESSAO personalizada para a camada de recurso
		return obj.orElseThrow(() -> new ObjectNotFoundExceptionEmerson(
				"Objeto não encontrado! Id: " + pId + ", Tipo: " + Cliente.class.getName()));
	}

	@Transactional // anotaçãoo aprendida na aula 43
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
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
			throw new DataIntegrityExceptionEmerson("Não é possível excluir porque ha pedidos relacionados");
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
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null, null);
	}

	// sobrecarga do metodo só que agora recebendo um ClienteNewDTO
	public Cliente fromDTO(ClienteNewDTO objDto) {

		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(),
				TipoCliente.toEnum(objDto.getTipo()), passwordEncoder.encode(objDto.getSenha()));
		Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(),
				objDto.getBairro(), objDto.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());

		if (objDto.getTelefone2() != null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}

		if (objDto.getTelefone3() != null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}

		return cli;
	}

	// aula 41
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}

	// aula 86
	public URI uploadProfilePicture(MultipartFile multipartFile) {

		// verifica se possui um usuário logado
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationExceptionEmerson("Acesso negado");
		}

		URI uri = s3Service.uploadFile(multipartFile);

		// na aula, a instanciação esta sendo feita com repo.findOne porém isso gera
		// erro
		Cliente cli = find(user.getId());
		cli.setImageUrl(uri.toString());
		repo.save(cli);

		return uri;
	}

}
