package com.nelioalves.cursomc.resources.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.nelioalves.cursomc.services.exceptions.DataIntegrityExceptionEmerson;
import com.nelioalves.cursomc.services.exceptions.ObjectNotFoundExceptionEmerson;

@ControllerAdvice // aula 20 - 12:15
public class ResourceExceptionHandlerEmerson {

	// padrao do da anotação @ControllerAdvice
	@ExceptionHandler(ObjectNotFoundExceptionEmerson.class)
	public ResponseEntity<StandardErrorEmerson> objectNotFoundEmerson(ObjectNotFoundExceptionEmerson pNotFound,
			HttpServletRequest pRequest) {

		// instancia novo erro padrao (objeto nao encontrado)
		StandardErrorEmerson err = new StandardErrorEmerson(HttpStatus.NOT_FOUND.value(), pNotFound.getMessage(),
				System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}

	// aula 36
	@ExceptionHandler(DataIntegrityExceptionEmerson.class)
	public ResponseEntity<StandardErrorEmerson> dataIntegrityEmerson(DataIntegrityExceptionEmerson pNotFound,
			HttpServletRequest pRequest) {

		// instancia novo erro padrao (objeto depende de outras classes/tabelas)
		StandardErrorEmerson err = new StandardErrorEmerson(HttpStatus.BAD_REQUEST.value(), pNotFound.getMessage(),
				System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

}
