package com.nelioalves.cursomc.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

/*
 * aula 101
 * 
 * filtro que intercepta todas requisições
 * 
 * anotação @Component - o professor não comentou nada sobre a anotação mas pelo que eu entendi, 
 * ela tem a função de interceptar todas requisições - neste caso foi criado a classe HeaderExposureFilter 
 * para aplicar um filtro que expoe o cabeçalho "location" ... em nenhum outro local nós chamamos algum 
 * metodo desta classe ou se quer chamamos a classe. O que me leva a crer que a 
 * simples anotação @Component faz este trabalho de interceptação*/
@Component
public class HeaderExposureFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletResponse res = (HttpServletResponse) response;
		res.addHeader("access-control-expose-headers", "location");
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}
}
