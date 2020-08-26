package com.nelioalves.cursomc.services.exceptions;

//aula 87
public class FileExceptionEmerson extends RuntimeException {

	private static final long serialVersionUID = 1L;

	// recebe uma string com a mensgem de excessao e repassa a mensagem pra quem a
	// chama
	public FileExceptionEmerson(String pMensagemExcessao) {
		super(pMensagemExcessao);
	}

	// sobrecarga - recebe a mensagem e uma outra excessão pCause de alguma coisa q
	// aconteceu antes
	public FileExceptionEmerson(String pMensagem, Throwable pCause) {
		super(pMensagem, pCause);
	}

}
