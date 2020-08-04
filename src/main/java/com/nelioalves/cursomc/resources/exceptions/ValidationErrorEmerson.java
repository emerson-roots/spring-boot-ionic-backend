package com.nelioalves.cursomc.resources.exceptions;

import java.util.ArrayList;
import java.util.List;

//aula 40
public class ValidationErrorEmerson extends StandardErrorEmerson {
	private static final long serialVersionUID = 1L;

	private List<FieldMessageEmerson> errors = new ArrayList<>();

	public ValidationErrorEmerson(Integer status, String msg, Long timeStamp) {
		super(status, msg, timeStamp);
	}

	public List<FieldMessageEmerson> getErrors() {
		return errors;
	}
	
	//aula 40
	public void addError(String fieldName, String message) {
		errors.add(new FieldMessageEmerson(fieldName, message));
	}
	
	

}
