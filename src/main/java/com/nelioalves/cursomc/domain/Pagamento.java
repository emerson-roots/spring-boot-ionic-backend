package com.nelioalves.cursomc.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nelioalves.cursomc.domain.enums.EstadoPagamento;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)// aula 27 - mapeamento de herança
public abstract class Pagamento implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//aula 26 - quando o relacionamento é 1 pra 1 - @OneToOne (herda ID de outra tabela)
	//nao é utilizado a anotação @GeneratedValue
	//pois o id nao sera gerado automaticamente .. sera utilizado o id da tabela principal
	@Id
	private Integer id;
	private Integer estadoPgto;
	
	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "pedido_id")
	@MapsId // aprendido na aula 26 - sobre relações OneToOne - 1 para 1
	private Pedido pedido;
	
	public Pagamento() {
	}
	
	public Pagamento(Integer id, EstadoPagamento estadoPgto, Pedido pedido) {
		super();
		this.id = id;
		this.estadoPgto = estadoPgto.getCod();
		this.pedido = pedido;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public EstadoPagamento getEstadoPgto() {
		return EstadoPagamento.toEnum(estadoPgto);
	}

	public void setEstadoPgto(EstadoPagamento estadoPgto) {
		this.estadoPgto = estadoPgto.getCod();
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pagamento other = (Pagamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	
	
}
