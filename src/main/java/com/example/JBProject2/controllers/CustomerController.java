package com.example.JBProject2.controllers;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.example.JBProject2.beans.CategoryType;
import com.example.JBProject2.beans.Coupon;
import com.example.JBProject2.facades.CustomerFacade;
import com.example.JBProject2.facades.exceptions.CouponAlreadyExistsException;
import com.example.JBProject2.facades.exceptions.CouponExpiredOrNoStockException;

@RestController
@RequestMapping("customer")
@CrossOrigin(origins="http://localhost:4200")
public class CustomerController {
	
	@Autowired
	CustomerFacade custFace;
	
	
	@GetMapping("coupons")
	public List<Coupon> getAllCustomerCoupons(){
		return custFace.getAllCustomerCoupons();
	}
	
	@GetMapping("coupons/category/{category}")
	public Set<Coupon> getCustomerCouponsByCategory(@PathVariable CategoryType category){
		return custFace.getCustomerCouponsByCategory(category);
	}
	
	@GetMapping("coupons/price/{price}")
	public Set<Coupon> getCustomerCouponsByPrice(@PathVariable double maxPrice){
		return custFace.getCustomerCouponsByPrice(maxPrice);
	}
	
	@PostMapping
	public RedirectView purchaseCoupon(@RequestBody Coupon coupon) throws CouponAlreadyExistsException, CouponExpiredOrNoStockException {
		custFace.purchaseCoupon(coupon);
		return new RedirectView("");
	}
	

}
