package com.example.JBProject2.facades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.JBProject2.beans.Coupon;
import com.example.JBProject2.db.CompanyRepository;
import com.example.JBProject2.db.CouponRepository;
import com.example.JBProject2.facades.exceptions.CouponNotFoundException;

@Service
public class CompanyFacade {
	
	@Autowired
	CouponRepository coupRepo;
	@Autowired
	CompanyRepository compRepo;
	
	/*
	 * Coupon getter methods
	 */	
	
	// Returns all coupons by 
	
	
	// Returns one coupon by ID
	public Coupon getOneCoupon(int coupId) throws CouponNotFoundException {
		if(coupRepo.findById(coupId).isPresent() && coupRepo.findById(coupId).get() instanceof Coupon)
			return coupRepo.findById(coupId).get();
		else throw new CouponNotFoundException();
	}

}
