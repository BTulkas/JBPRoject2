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
import com.example.JBProject2.beans.CategoryType;
import com.example.JBProject2.beans.Coupon;
import com.example.JBProject2.beans.Session;
import com.example.JBProject2.controllers.exceptions.AccessDeniedException;
import com.example.JBProject2.controllers.exceptions.LoginExpiredException;
import com.example.JBProject2.facades.AdminFacade;
import com.example.JBProject2.facades.ClientFacade;
import com.example.JBProject2.facades.CustomerFacade;
import com.example.JBProject2.facades.exceptions.CouponAlreadyExistsException;
import com.example.JBProject2.facades.exceptions.CouponExpiredOrNoStockException;
import com.example.JBProject2.facades.exceptions.CouponNotFoundException;

@RestController
@RequestMapping("customer")
@CrossOrigin(origins="http://localhost:4200")
public class CustomerController {
	
	@Autowired
	CustomerFacade custFace;
	
	@Autowired
	private Map<String, Session> sessions;
	
	
	private CustomerFacade checkFacade(String token) throws AccessDeniedException, LoginExpiredException {
		ClientFacade loggedCustomer = sessions.get(token).getFacade();
		Session lastAction = sessions.get(token);
		
		if(!(loggedCustomer instanceof CustomerFacade))
			throw new AccessDeniedException();
		if(System.currentTimeMillis() - lastAction.getLastActive() > 1000*60*1) {
			throw new LoginExpiredException();
		}
		
		lastAction.setLastActive(System.currentTimeMillis());
		return (CustomerFacade)loggedCustomer;
	}
	

	@GetMapping("{token}/coupons")
	public List<Coupon> getAllCustomerCoupons(@PathVariable String token) throws AccessDeniedException, LoginExpiredException{
		CustomerFacade loggedCust = checkFacade(token);
		return loggedCust.getAllCustomerCoupons();

	}
	
	@GetMapping("{token}/coupons/category/{category}")
	public Set<Coupon> getCustomerCouponsByCategory(@PathVariable String token, @PathVariable CategoryType category) throws AccessDeniedException, LoginExpiredException{
		CustomerFacade loggedCust = checkFacade(token);
		return loggedCust.getCustomerCouponsByCategory(category);
	
	}
	
	@GetMapping("{token}/coupons/price/{maxPrice}")
	public Set<Coupon> getCustomerCouponsByPrice(@PathVariable String token, @PathVariable double maxPrice) throws AccessDeniedException, LoginExpiredException{
		CustomerFacade loggedCust = checkFacade(token);
		return loggedCust.getCustomerCouponsByPrice(maxPrice);

	}
	
	@PostMapping("/{token}")
	public void purchaseCoupon(@PathVariable String token, @RequestBody int couponId) throws CouponAlreadyExistsException, CouponExpiredOrNoStockException, CouponNotFoundException, AccessDeniedException, LoginExpiredException {
		CustomerFacade loggedCust = checkFacade(token);
		loggedCust.purchaseCoupon(couponId);
	}
	

}
