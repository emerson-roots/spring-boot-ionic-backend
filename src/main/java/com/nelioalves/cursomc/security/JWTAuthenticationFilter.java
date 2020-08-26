package com.nelioalves.cursomc.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nelioalves.cursomc.dto.CredenciaisDTO;

//aula 72
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	// injetados direto no construtor
	private AuthenticationManager authenticationManager;
	private JWTUtil jwtUtil;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
		setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException {

		try {
			// pega dados que vieram da requisição LOGIN e convertelos para o tipo
			// CredenciaisDTO
			CredenciaisDTO creds = new ObjectMapper().readValue(req.getInputStream(), CredenciaisDTO.class);

			// pegando os dados, instancia um objeto do tipo
			// UsernamePasswordAuthenticationToken (nao e o token do jwt, mas sim do spring
			// security)
			// passando o usuario/email .. a senha e uma lista vazia por eqto
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(creds.getEmail(),
					creds.getSenha(), new ArrayList<>());

			// de posse do objeto authToken, é chamado o argumento injetado no construtor
			// authenticationManager .authenticate .. este e oo metodo que vai verificar se
			// o usuario e senha sao validos.
			// o framework faz isso com base no que foi implementado no userDetailsService e
			// UserDetails. Ele usa os contratos implemetados e verifica se sao um usuario e
			// senha validos.
			Authentication auth = authenticationManager.authenticate(authToken);

			// sendo valido ou não o "Authentication auth" que vai informar para o Spring
			// Security se a autenticação ocorreu com sucesso ou nao. Se for correto vai
			// passar, do contrario da um Bad Credentials
			return auth;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	// se a autenticação foi valida no metodo attemptAuthentication, então ele
	// retorna a requisição com um token através deste metodo
	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Authentication auth) throws IOException, ServletException {

		String username = ((UserSS) auth.getPrincipal()).getUsername();
		String token = jwtUtil.generateToken(username);
		res.addHeader("Authorization", "Bearer " + token);

		/**
		 * aula 93 - expoe o cabeçalho Authorization no header pois sem isto, a
		 * aplicação/backend não consegue acessar os valores do cabeçalho
		 */
		res.addHeader("access-control-expose-headers", "Authorization");
	}

	// aula 71
	// correção/atualização para spring boot 2.x.x
	private class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {

		@Override
		public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
				AuthenticationException exception) throws IOException, ServletException {
			response.setStatus(401);
			response.setContentType("application/json");
			response.getWriter().append(json());
		}

		private String json() {
			long date = new Date().getTime();
			return "{\"timestamp\": " + date + ", " + "\"status\": 401, " + "\"error\": \"Não autorizado\", "
					+ "\"message\": \"Email ou senha inválidos\", " + "\"path\": \"/login\"}";
		}
	}

}
