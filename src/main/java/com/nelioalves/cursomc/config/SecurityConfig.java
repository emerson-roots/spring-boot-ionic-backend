package com.nelioalves.cursomc.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * foi utilizado como base a seguinte fonte para a implementação desta classe;
 * 
 * https://auth0.com/blog/implementing-jwt-authentication-on-spring-boot/
 * 
 * link fornecido pelo professor no PDF do material de apoio do capitulo
 * 
 */

//aula 68
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private Environment env;

	private static final String[] PUBLIC_MATCHERS = { "/h2-console/**" };

	// permite somente leitura
	private static final String[] PUBLIC_MATCHERS_GET = { "/produtos/**", "/categorias/**" };

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// verifica profiles ativos do projeto
		// se estiver no profile test, significa que queremos acessar o H
		if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
			http.headers().frameOptions().disable();
		}

		// desabilita proteção de ataques a CSRF pois nosso sistema sera "STATELESS"
		// que significa que nao armazena seção de usuário
		http.cors().and().csrf().disable();

		// todos os caminhos que estiverem no vetor, será permitido
		// e para todo o resto exige autenticação
		http.authorizeRequests().antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll()
				.antMatchers(PUBLIC_MATCHERS).permitAll().anyRequest().authenticated();

		// define que a aplicação é STATELESS - assegura que nosso back end nao cria
		// seção de usuário
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}

}
