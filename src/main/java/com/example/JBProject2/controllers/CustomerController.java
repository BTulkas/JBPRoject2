package com.example.JBProject2.controllers;

import java.util.List;
import java.util.Map;
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
import com.example.JBProject2.facades.ClientFacade;
import com.example.JBProject2.facades.CustomerFacade;
import com.example.JBProject2.facades.exceptions.CouponAlreadyExistsException;
import com.example.JBProject2.facades.exceptions.CouponExpiredOrNoStockException;
import com.example.JBProject2.facades.exceptions.CouponNotFoundException;
import com.example.JBProject2.facades.exceptions.CustomerNotFoundException;

@RestController
@RequestMapping("customer")
@CrossOrigin(origins="http://localhost:4200")
public class CustomerController {
	
	@Autowired
	CustomerFacade custFace;
	
	//@Autowired
	private Map<String, ClientFacade> sessions;
	
	
	
	
	public CustomerController(Map<String, ClientFacade> sessions) {
		//super();
		System.out.println("ctor: " + sessions);
		this.sessions = sessions;
	}

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
	
	@PostMapping("/{token}")
	public void purchaseCoupon(@PathVariable String token, @RequestBody int couponId) throws CouponAlreadyExistsException, CouponExpiredOrNoStockException, CouponNotFoundException, CustomerNotFoundException {
		ClientFacade loggedCust = sessions.get(token);
		System.out.println("purchase coupone" + sessions);
		if(loggedCust instanceof CustomerFacade) {
				((CustomerFacade) loggedCust).purchaseCoupon(couponId);
		}
	
		else throw new CustomerNotFoundException();
		//return new RedirectView("");
	}
	

}
