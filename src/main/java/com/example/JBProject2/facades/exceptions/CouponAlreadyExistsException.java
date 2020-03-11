package com.example.JBProject2.facades.exceptions;

public class CouponAlreadyExistsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CouponAlreadyExistsException() {
		super("There is already a coupon by that title.");
	}

}
