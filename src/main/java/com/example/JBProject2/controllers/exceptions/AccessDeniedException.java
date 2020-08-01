package com.example.JBProject2.controllers.exceptions;

public class AccessDeniedException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccessDeniedException() {
		super("Access denied!");
	}

}
