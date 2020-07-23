package com.nelioalves.cursomc.domain.enums;


// aula 22 - aprendido sobre enums
public enum TipoCliente {

	PESSOAFISICA(1,"Pessoa Física"),
	PESSOAJURIDICA(2,"Pessoa Jurídica");
	
	private int cod;
	private String descricao;
	
	private TipoCliente(int cod, String descricao) {
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
	public static TipoCliente toEnum(Integer cod) {
		
		//proteção - se for nulo, retorna nulo
		if(cod == null) {
			return null;
		}
		
		//percorre todos valores possiveis do tipo enumerado TipoCliente de acordo com as declarações
		for (TipoCliente tipoCliente : TipoCliente.values()) {
			if(cod.equals(tipoCliente.getCod())) {
				return tipoCliente;
			}
			
		}
		//caso esgote o for e não retornar nenhum valor valido - lança exception
		throw new IllegalArgumentException("Id Inválido: "+cod);
		
		
	}
	
}
