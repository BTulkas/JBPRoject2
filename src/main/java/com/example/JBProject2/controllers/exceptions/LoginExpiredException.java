package com.example.JBProject2.controllers.exceptions;

public class LoginExpiredException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public LoginExpiredException() {
		super("You have been inactive for too long. Just like your parents keep saying.");
	}

}
