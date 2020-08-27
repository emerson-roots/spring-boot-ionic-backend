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

	/**
	 * modificado na aula 98
	 * 
	 */
	// padrao do da anotação @ControllerAdvice
	@ExceptionHandler(ObjectNotFoundExceptionEmerson.class)
	public ResponseEntity<StandardErrorEmerson> objectNotFoundEmerson(ObjectNotFoundExceptionEmerson pNotFound,
			HttpServletRequest pRequest) {

		// instancia novo erro padrao (objeto nao encontrado)
		StandardErrorEmerson err = new StandardErrorEmerson(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(),
				"Não encontrado", pNotFound.getMessage(), pRequest.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}

	// aula 36
	/**
	 * modificado na aula 98
	 * 
	 */
	@ExceptionHandler(DataIntegrityExceptionEmerson.class)
	public ResponseEntity<StandardErrorEmerson> dataIntegrityEmerson(DataIntegrityExceptionEmerson e,
			HttpServletRequest pRequest) {

		// instancia novo erro padrao (objeto depende de outras classes/tabelas)
		StandardErrorEmerson err = new StandardErrorEmerson(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
				"Integridade de dados", e.getMessage(), pRequest.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

	// aula 40
	/**
	 * modificado na aula 98
	 * 
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardErrorEmerson> validationEmerson(MethodArgumentNotValidException e,
			HttpServletRequest pRequest) {

		// instancia novo erro padrao (objeto depende de outras classes/tabelas)
		ValidationErrorEmerson err = new ValidationErrorEmerson(System.currentTimeMillis(),
				HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validação", e.getMessage(), pRequest.getRequestURI());

		// percorre cada erro adicionando ao metodo personalizado addError
		for (FieldError x : e.getBindingResult().getFieldErrors()) {
			err.addError(x.getField(), x.getDefaultMessage());
		}
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(err);
	}

	/**
	 * AULA 75
	 * 
	 * ====================================
	 * 
	 * modificado na aula 98
	 */
	@ExceptionHandler(AuthorizationExceptionEmerson.class)
	public ResponseEntity<StandardErrorEmerson> authorizationEmerson(AuthorizationExceptionEmerson e,
			HttpServletRequest pRequest) {

		StandardErrorEmerson err = new StandardErrorEmerson(System.currentTimeMillis(), HttpStatus.FORBIDDEN.value(),
				"Acesso negado", e.getMessage(), pRequest.getRequestURI());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
	}

	/**
	 * AULA 87
	 * 
	 * ==================================
	 * 
	 * modificado na aula 98
	 * 
	 */
	@ExceptionHandler(FileExceptionEmerson.class)
	public ResponseEntity<StandardErrorEmerson> fileEmerson(FileExceptionEmerson e, HttpServletRequest pRequest) {

		StandardErrorEmerson err = new StandardErrorEmerson(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
				"Erro de arquivo", e.getMessage(), pRequest.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

	/**
	 * aula 87
	 * 
	 * tratamento de exceção especifica da AMAZON
	 * 
	 * ==========================================
	 * 
	 * modificado na aula 98
	 * 
	 */
	@ExceptionHandler(AmazonServiceException.class)
	public ResponseEntity<StandardErrorEmerson> amazonServiceEmerson(AmazonServiceException e,
			HttpServletRequest pRequest) {

		// armazena o status/exception HTTP especifico da amazon
		HttpStatus code = HttpStatus.valueOf(e.getErrorCode());

		StandardErrorEmerson err = new StandardErrorEmerson(System.currentTimeMillis(), code.value(),
				"Erro Amazon Service", e.getMessage(), pRequest.getRequestURI());
		return ResponseEntity.status(code).body(err);
	}

	/**
	 * AULA 87
	 * 
	 * ============================================
	 * 
	 * modificado na aula 98
	 * 
	 */
	@ExceptionHandler(AmazonClientException.class)
	public ResponseEntity<StandardErrorEmerson> amazonClientEmerson(AmazonClientException e,
			HttpServletRequest pRequest) {

		StandardErrorEmerson err = new StandardErrorEmerson(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
				"Erro Amazon Client", e.getMessage(), pRequest.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

	/**
	 * AULA 87
	 * 
	 * ====================================
	 * 
	 * modificado na aula 98
	 * 
	 */
	@ExceptionHandler(AmazonS3Exception.class)
	public ResponseEntity<StandardErrorEmerson> amazonS3Emerson(AmazonS3Exception e, HttpServletRequest pRequest) {

		StandardErrorEmerson err = new StandardErrorEmerson(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
				"Erro S3", e.getMessage(), pRequest.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

}
