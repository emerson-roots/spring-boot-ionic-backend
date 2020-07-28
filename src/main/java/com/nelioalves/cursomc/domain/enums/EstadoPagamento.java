package com.nelioalves.cursomc.domain.enums;

//aula 25
public enum EstadoPagamento {

	PENDENTE(1,"Pendente"),
	QUITADO(2,"Quitado"),
	CANCELADO(3,"Cancelado");
	
	private Integer cod;
	private String descricao;
	
	
	
	
	private EstadoPagamento(Integer cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
	
	public Integer getCod() {
		return cod;
	}
	public String getDescricao() {
		return descricao;
	}
	
	//static para nao ser necessario instanciar
		public static EstadoPagamento toEnum(Integer cod) {
			
			//proteção - se for nulo, retorna nulo
			if(cod == null) {
				return null;
			}
			
			//percorre todos valores possiveis do tipo enumerado EstadoPagamento de acordo com as declarações
			for (EstadoPagamento estadoPagamento : EstadoPagamento.values()) {
				if(cod.equals(estadoPagamento.getCod())) {
					return estadoPagamento;
				}
				
			}
			//caso esgote o for e não retornar nenhum valor valido - lança exception
			throw new IllegalArgumentException("Id Inválido: "+cod);
			
			
		}
	
	
	
}
