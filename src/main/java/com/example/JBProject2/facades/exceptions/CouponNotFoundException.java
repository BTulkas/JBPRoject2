package com.example.JBProject2.facades.exceptions;

public class CouponNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CouponNotFoundException() {
		super("Coupon not found.");
	}

}
