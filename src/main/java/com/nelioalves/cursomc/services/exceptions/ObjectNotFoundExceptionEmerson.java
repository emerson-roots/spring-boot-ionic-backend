package com.nelioalves.cursomc.services.exceptions;


//aula 20 - implementação padrao de excessão personalizada
public class ObjectNotFoundExceptionEmerson extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	// recebe uma string com a mensgem de excessao e repassa a mensagem pra quem a chama
	public ObjectNotFoundExceptionEmerson(String pMensagemExcessao) {
		super(pMensagemExcessao);
	}
	
	
	// sobrecarga - recebe a mensagem e uma outra excessão pCause de alguma coisa q aconteceu antes
	public ObjectNotFoundExceptionEmerson(String pMensagem, Throwable pCause) {
		super(pMensagem, pCause);
	}

}
