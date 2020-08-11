package com.nelioalves.cursomc.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nelioalves.cursomc.domain.Categoria;
import com.nelioalves.cursomc.domain.Produto;

// aula 17 - 10:57 - repositories sao capazes de operações d acesso a dados (alterar, inserir, deletar, etx...)
// JpaRepository - é um tipo especial do spring capaz de acessar dados com base em um tipo, neste caso Categoria
// e o atributo identificador que no caso sao as IDs dos dados
@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

	/**
	 * aula 50 exemplo usando consulta JPQL direto - faz a mesma função que o metodo
	 * findDistinctByNomeContainingAndCategoriasIn
	 * 
	 * S E M  ---- U S O
	 */
	@Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat WHERE obj.nome LIKE %:nome% AND cat IN :categorias")
	Page<Produto> search(@Param("nome") String nome, @Param("categorias") List<Categoria> categorias,
			Pageable pageRequest);

	/**
	 * aula 51 exemplo usando padrao de nomes citado na aula 50 e 51 - onde o
	 * framework cria automaticamente a sintaxe JPQL se usarmos os padroes de nomes
	 * descritos na documentação
	 */
	@Transactional(readOnly = true)
	Page<Produto> findDistinctByNomeContainingAndCategoriasIn(String nome, List<Categoria> categorias,
			Pageable pageRequest);
}
