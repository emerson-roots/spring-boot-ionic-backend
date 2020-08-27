package com.nelioalves.cursomc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nelioalves.cursomc.domain.Estado;

// aula 17 - 10:57 - repositories sao capazes de operações d acesso a dados (alterar, inserir, deletar, etx...)
// JpaRepository - é um tipo especial do spring capaz de acessar dados com base em um tipo, neste caso Categoria
// e o atributo identificador que no caso sao as IDs dos dados
@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {

	// aula 97 - utilizando padrão de nomes do spring data
	@Transactional(readOnly = true)
	public List<Estado> findAllByOrderByNome();

}
