package com.nelioalves.cursomc.resources.exceptions;

import java.io.Serializable;

// aula 20 - objeto auxiliar da classe ResourceExceptionHandlerEmerson
// necessario para gerar um erro bonito, e um JSon informando codigo HTTp, mensagem de erro e instante que ocorreu o erro
/**
 * 
 * classe modificada na aula 98 para padronizar os erros de acordo com os erros
 * do framwork
 * 
 */
public class StandardErrorEmerson implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long timestamp;
	private Integer status;
	private String error;
	private String message;
	private String path;

	public StandardErrorEmerson(Long timestamp, Integer status, String error, String message, String path) {
		super();
		this.timestamp = timestamp;
		this.status = status;
		this.error = error;
		this.message = message;
		this.path = path;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
