package com.example.JBProject2.login_manager.exception;

public class WrongLoginException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public WrongLoginException() {
		super("Email or password incorrect.");
	}

}
