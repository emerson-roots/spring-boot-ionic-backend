package com.nelioalves.cursomc.resources.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
	public ResponseEntity<StandardErrorEmerson> dataIntegrityEmerson(DataIntegrityExceptionEmerson e,
			HttpServletRequest pRequest) {

		// instancia novo erro padrao (objeto depende de outras classes/tabelas)
		StandardErrorEmerson err = new StandardErrorEmerson(HttpStatus.BAD_REQUEST.value(), e.getMessage(),
				System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

	// aula 40
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardErrorEmerson> validationEmerson(MethodArgumentNotValidException e,
			HttpServletRequest pRequest) {

		// instancia novo erro padrao (objeto depende de outras classes/tabelas)
		ValidationErrorEmerson err = new ValidationErrorEmerson(HttpStatus.BAD_REQUEST.value(), "Erro de validacao de campos",
				System.currentTimeMillis());
		
		//percorre cada erro adicionando ao metodo personalizado addError
		for (FieldError x : e.getBindingResult().getFieldErrors()) {
			err.addError(x.getField(), x.getDefaultMessage());
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

}
