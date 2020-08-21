package com.nelioalves.cursomc.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

//aula 71
@Component
public class JWTUtil {

	// gera token de acordo com a palavra chave no appliocation.properties
	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private Long expiration;

	public String generateToken(String username) {
		// o .builder é quem gera o token
		// setSubject é o usuario
		// setExpiration faz um calculo, pegando
		// a data atual do sistema e adicionando
		// o tempo definido no application.properties
		// .signwith - diz como sera assinado o token (SignatureAlgorithm.HS512 é
		// poderoso e o outro argumento é a palavra/string secret convertida em bytes)
		return Jwts.builder().setSubject(username).setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes()).compact();
	}

	// aula 73
	public boolean tokenValido(String token) {

		// Claimns é um tipo do JWT que armazena as reivindicações do token
		Claims claims = getClaims(token);

		if (claims != null) {
			String username = claims.getSubject();
			Date expirationDate = claims.getExpiration();
			Date now = new Date(System.currentTimeMillis());

			// testa se username nao e nulo / se a data do token comparando com a data atual
			// nao for nulo
			if (username != null && expirationDate != null && now.before(expirationDate)) {
				return true;
			}

		}
		// se algo falhar retorna falso
		return false;
	}

	// aula 73
	// pega o usuario a partir do token
	public String getUsername(String token) {

		// Claimns é um tipo do JWT que armazena as reivindicações do token
		Claims claims = getClaims(token);
		if (claims != null) {
			return claims.getSubject();
		}
		return null;
	}

	// aula 73
	// função que recupera os Claimns a partir de um token
	private Claims getClaims(String token) {

		// tenta pegar o claimns do token
		// caso seja invalido ou de problemas, retorna nulo
		try {
			return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			return null;
		}

	}

}
