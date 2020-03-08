package com.example.JBProject2.facades.exceptions;

public class CompanyAlreadyExistsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CompanyAlreadyExistsException() {
		super("A company with that name or email already exists.");
	}

}
