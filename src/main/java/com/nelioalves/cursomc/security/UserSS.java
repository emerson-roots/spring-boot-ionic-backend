package com.nelioalves.cursomc.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.nelioalves.cursomc.domain.enums.Perfil;

//aula 71
public class UserSS implements UserDetails {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String email;
	private String senha;
	private Collection<? extends GrantedAuthority> authorities;

	public UserSS() {

	}

	public UserSS(Integer id, String email, String senha, Set<Perfil> perfis) {
		super();
		this.id = id;
		this.email = email;
		this.senha = senha;
		// faz a conversão dos perfis para o tipo
		// Collection<? extends GrantedAuthority> authorities
		// exigido pelo Spring Security
		this.authorities = perfis.stream().map(x -> new SimpleGrantedAuthority(x.getDescricao()))
				.collect(Collectors.toList());
	}

	public Integer getId() {
		return id;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public String getUsername() {
		return email;
	}

	// metodo que implementa se a conta/sessão de usuário esta expirada
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	// metodo que implementa se a conta/sessão esta bloqueada
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	// implementa se as credenciais estão expiradas ou não
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	// usuário ativo/inativo
	@Override
	public boolean isEnabled() {
		return true;
	}

	/**
	 * aula 75
	 * 
	 * testa se um usuário possui um perfil especifico (ADMIN OU CLIENTE
	 */
	public boolean hasRole(Perfil perfil) {
		return getAuthorities().contains(new SimpleGrantedAuthority(perfil.getDescricao()));
	}

}
