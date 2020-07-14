package com.example.JBProject2.facades;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.example.JBProject2.beans.Coupon;
import com.example.JBProject2.db.CouponRepository;

@Service
@Scope("prototype")
public class GenericFacade extends ClientFacade {
	
	@Autowired
	CouponRepository coupRepo;
	
	public List<Coupon> getAllCoupons(){
		return coupRepo.findAll();
	}
}
