package com.example.JBProject2.facades.exceptions;

public class CouponExpiredOrNoStockException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CouponExpiredOrNoStockException() {
		super("This coupon is expired or out of stock.");
	}

}
