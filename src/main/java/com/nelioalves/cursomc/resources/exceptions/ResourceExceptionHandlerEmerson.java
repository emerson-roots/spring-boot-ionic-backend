package com.nelioalves.cursomc.resources.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.nelioalves.cursomc.services.exceptions.AuthorizationExceptionEmerson;
import com.nelioalves.cursomc.services.exceptions.DataIntegrityExceptionEmerson;
import com.nelioalves.cursomc.services.exceptions.FileExceptionEmerson;
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
		ValidationErrorEmerson err = new ValidationErrorEmerson(HttpStatus.BAD_REQUEST.value(),
				"Erro de validacao de campos", System.currentTimeMillis());

		// percorre cada erro adicionando ao metodo personalizado addError
		for (FieldError x : e.getBindingResult().getFieldErrors()) {
			err.addError(x.getField(), x.getDefaultMessage());
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

	/**
	 * AULA 75
	 * 
	 */
	@ExceptionHandler(AuthorizationExceptionEmerson.class)
	public ResponseEntity<StandardErrorEmerson> authorizationEmerson(AuthorizationExceptionEmerson e,
			HttpServletRequest pRequest) {

		StandardErrorEmerson err = new StandardErrorEmerson(HttpStatus.FORBIDDEN.value(), e.getMessage(),
				System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
	}
	
	/**
	 * AULA 87
	 * 
	 */
	@ExceptionHandler(FileExceptionEmerson.class)
	public ResponseEntity<StandardErrorEmerson> fileEmerson(FileExceptionEmerson e,
			HttpServletRequest pRequest) {

		StandardErrorEmerson err = new StandardErrorEmerson(HttpStatus.BAD_REQUEST.value(), e.getMessage(),
				System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	/**
	 * aula 87
	 * 
	 * tratamento de exceção especifica da AMAZON
	 * 
	 * */
	@ExceptionHandler(AmazonServiceException.class)
	public ResponseEntity<StandardErrorEmerson> amazonServiceEmerson(AmazonServiceException e,
			HttpServletRequest pRequest) {
		
		//armazena o status/exception HTTP especifico da amazon
		HttpStatus code = HttpStatus.valueOf(e.getErrorCode());
		
		StandardErrorEmerson err = new StandardErrorEmerson(code.value(), e.getMessage(),
				System.currentTimeMillis());
		return ResponseEntity.status(code).body(err);
	}
	
	/**
	 * AULA 87
	 * 
	 */
	@ExceptionHandler(AmazonClientException.class)
	public ResponseEntity<StandardErrorEmerson> amazonClientEmerson(AmazonClientException e,
			HttpServletRequest pRequest) {

		StandardErrorEmerson err = new StandardErrorEmerson(HttpStatus.BAD_REQUEST.value(), e.getMessage(),
				System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	/**
	 * AULA 87
	 * 
	 */
	@ExceptionHandler(AmazonS3Exception.class)
	public ResponseEntity<StandardErrorEmerson> amazonS3Emerson(AmazonS3Exception e,
			HttpServletRequest pRequest) {

		StandardErrorEmerson err = new StandardErrorEmerson(HttpStatus.BAD_REQUEST.value(), e.getMessage(),
				System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

}
