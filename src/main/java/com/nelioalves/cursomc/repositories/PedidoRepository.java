package com.nelioalves.cursomc.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nelioalves.cursomc.domain.Cliente;
import com.nelioalves.cursomc.domain.Pedido;

// aula 17 - 10:57 - repositories sao capazes de operações d acesso a dados (alterar, inserir, deletar, etx...)
// JpaRepository - é um tipo especial do spring capaz de acessar dados com base em um tipo, neste caso Pedido
// e o atributo identificador que no caso sao as IDs dos dados
@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

	// aula 76
	// utilizando padrão de nomes
	@Transactional(readOnly = true)
	Page<Pedido> findByCliente(Cliente cliente, Pageable pageRequest);

}
