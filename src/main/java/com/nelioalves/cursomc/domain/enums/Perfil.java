package com.nelioalves.cursomc.domain.enums;


// aula 70
public enum Perfil {

	ADMIN(1,"ROLE_ADMIN"),
	CLIENTE(2,"ROLE_CLIENTE");
	
	private int cod;
	private String descricao;
	
	private Perfil(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}
	
	//static para nao ser necessario instanciar
	public static Perfil toEnum(Integer cod) {
		
		//proteção - se for nulo, retorna nulo
		if(cod == null) {
			return null;
		}
		
		//percorre todos valores possiveis do tipo enumerado TipoCliente de acordo com as declarações
		for (Perfil tipoCliente : Perfil.values()) {
			if(cod.equals(tipoCliente.getCod())) {
				return tipoCliente;
			}
			
		}
		//caso esgote o for e não retornar nenhum valor valido - lança exception
		throw new IllegalArgumentException("Id Inválido: "+cod);
		
		
	}
	
}
