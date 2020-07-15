package com.nelioalves.cursomc.resources.exceptions;

import java.io.Serializable;

// aula 20 - objeto auxiliar da classe ResourceExceptionHandlerEmerson
// necessario para gerar um erro bonito, e um JSon informando codigo HTTp, mensagem de erro e instante que ocorreu o erro
public class StandardErrorEmerson implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer status; // aula 20 - status HTTP do erro
	private String msg; // aula 20 - mensagem do erro
	private Long timeStamp; // aula 20 - instante do erro

	public StandardErrorEmerson(Integer status, String msg, Long timeStamp) {
		super();
		this.status = status;
		this.msg = msg;
		this.timeStamp = timeStamp;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}

}
