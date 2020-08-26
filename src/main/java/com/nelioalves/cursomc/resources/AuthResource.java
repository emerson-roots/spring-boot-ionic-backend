package com.nelioalves.cursomc.resources;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nelioalves.cursomc.dto.EmailDTO;
import com.nelioalves.cursomc.security.AuthService;
import com.nelioalves.cursomc.security.JWTUtil;
import com.nelioalves.cursomc.security.UserSS;
import com.nelioalves.cursomc.services.UserService;

//aula 77
@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

	@Autowired
	private JWTUtil jwtUtil;

	@Autowired
	private AuthService service;

	/**
	 * endpoint protegido por autenticação o usuario tem que estar logado caso
	 * contrario gera uma excessão Forbidden
	 */
	@RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {

		// armazena o usuario logado na sessão
		UserSS user = UserService.authenticated();

		// gera um novo token para o osuario atualmente logado
		String token = jwtUtil.generateToken(user.getUsername());

		// adiciona o token na resposta da requisição
		response.addHeader("Authorization", "Bearer " + token);

		/**
		 * aula 93 - expoe o cabeçalho Authorization no header pois sem isto, a
		 * aplicação/backend não consegue acessar os valores do cabeçalho
		 */
		response.addHeader("access-control-expose-headers", "Authorization");

		return ResponseEntity.noContent().build();
	}

	// aula 78
	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO objDto) {
		service.sendNewPassword(objDto.getEmail());
		return ResponseEntity.noContent().build();

	}

}
