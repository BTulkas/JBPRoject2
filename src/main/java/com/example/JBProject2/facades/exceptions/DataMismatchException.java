package com.example.JBProject2.facades.exceptions;

public class DataMismatchException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DataMismatchException() {
		super("Did you try chaning something you shouldn't have?");
	}

}
