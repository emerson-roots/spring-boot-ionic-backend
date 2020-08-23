package com.nelioalves.cursomc.resources;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nelioalves.cursomc.security.JWTUtil;
import com.nelioalves.cursomc.security.UserSS;
import com.nelioalves.cursomc.services.UserService;

//aula 77
@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

	@Autowired
	private JWTUtil jwtUtil;

	/**
	 * endpoint protegido por autenticação o usuario tem que estar logado caso
	 * contrario gera uma excessão Forbidden
	 */
	@RequestMapping(value = "refresh_token", method = RequestMethod.POST)
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {

		// armazena o usuario logado na sessão
		UserSS user = UserService.authenticated();

		// gera um novo token para o osuario atualmente logado
		String token = jwtUtil.generateToken(user.getUsername());

		// adiciona o token na resposta da requisição
		response.addHeader("Authorization", "Bearer " + token);

		return ResponseEntity.noContent().build();
	}

}
