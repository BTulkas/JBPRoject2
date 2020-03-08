package com.example.JBProject2.facades.exceptions;

public class CustomerAlreadyExistsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CustomerAlreadyExistsException() {
		super("A customer already exists with this email.");
	}

}
