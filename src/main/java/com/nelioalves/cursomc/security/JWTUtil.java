package com.nelioalves.cursomc.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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

}
