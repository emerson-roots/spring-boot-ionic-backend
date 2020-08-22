package com.nelioalves.cursomc.services.exceptions;

//aula 75 - 3:48
public class AuthorizationExceptionEmerson extends RuntimeException {

	private static final long serialVersionUID = 1L;

	// recebe uma string com a mensgem de excessao e repassa a mensagem pra quem a
	// chama
	public AuthorizationExceptionEmerson(String pMensagemExcessao) {
		super(pMensagemExcessao);
	}

	// sobrecarga - recebe a mensagem e uma outra excessão pCause de alguma coisa q
	// aconteceu antes
	public AuthorizationExceptionEmerson(String pMensagem, Throwable pCause) {
		super(pMensagem, pCause);
	}

}
