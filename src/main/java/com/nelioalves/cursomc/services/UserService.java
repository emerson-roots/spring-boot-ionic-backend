package com.nelioalves.cursomc.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.nelioalves.cursomc.security.UserSS;

//aula 75
public class UserService {

	// método que retorna um usuario logado na forma de um usuário do spring
	// security
	public static UserSS authenticated() {

		// tratamento de excessão - pode gerar excessão caso o metodo
		// SecurityContextHolder retorne um usuário inexistente no banco
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}

	}

}
