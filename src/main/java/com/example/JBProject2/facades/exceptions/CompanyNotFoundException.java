package com.example.JBProject2.facades.exceptions;

public class CompanyNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CompanyNotFoundException() {
		super("Company not found.");
	}

}
