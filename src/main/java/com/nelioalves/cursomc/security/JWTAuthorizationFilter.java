package com.nelioalves.cursomc.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

//aula 73
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	private JWTUtil jwtUtil;

	// esse filtro analisa o token para ver se o token é valido e para isso sera
	// necessario extrair o usuario, buscar no banco de dados e ver se ele realmente
	// existe
	private UserDetailsService userDetailsService;

	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil,
			UserDetailsService userDetailsService) {
		super(authenticationManager);

		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;

	}

	// metodo que intercepta a requisição e verifica se o usuario esta autorizado
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		// armazena o valor do cabeçalho da requisição authorization
		// que no caso sera a chave token gerada (Bearer+Token)
		String header = request.getHeader("Authorization");

		// verifica se o cabeçalho nao retornou nulo e se ele retornou
		// um cabeçalho iniciando com a palavra "Bearer+Espaço"
		if (header != null && header.startsWith("Bearer ")) {

			// pega a string header e retira os 7 primeiros caracteres, para deixar somente
			// a chave/token excluindo "Bearer "
			UsernamePasswordAuthenticationToken auth = getAuthentication(header.substring(7));

			if (auth != null) {
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		}

		chain.doFilter(request, response);
	}

	// aula 73
	private UsernamePasswordAuthenticationToken getAuthentication(String token) {

		if (jwtUtil.tokenValido(token)) {

			String username = jwtUtil.getUsername(token);

			// busca no banco de dados o usuario
			UserDetails user = userDetailsService.loadUserByUsername(username);

			return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		}

		return null;
	}

}
