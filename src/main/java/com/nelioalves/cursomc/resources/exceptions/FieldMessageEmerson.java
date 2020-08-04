package com.nelioalves.cursomc.resources.exceptions;

import java.io.Serializable;


//aula 40
public class FieldMessageEmerson implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String fieldName;
	private String message;
	
	public FieldMessageEmerson() {
		
	}

	public FieldMessageEmerson(String fieldName, String message) {
		super();
		this.fieldName = fieldName;
		this.message = message;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
