package com.example.JBProject2.facades;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.JBProject2.beans.CategoryType;
import com.example.JBProject2.beans.Coupon;
import com.example.JBProject2.beans.Customer;
import com.example.JBProject2.db.CouponRepository;
import com.example.JBProject2.db.CustomerRepository;
import com.example.JBProject2.facades.exceptions.CouponAlreadyExistsException;
import com.example.JBProject2.facades.exceptions.CouponExpiredOrNoStockException;

@Service
public class CustomerFacade extends ClientFacade {
	
	private int loggedCustomerId;
	
	@Autowired
	CustomerRepository cusRepo;
	@Autowired
	CouponRepository coupRepo;
	
	/*
	 * public CustomerFacade(CustomerRepository cusFace) { super(); this.cusRepo =
	 * cusFace; }
	 * 
	 * public CustomerFacade(CouponRepository coupFace) { super(); this.coupRepo =
	 * coupFace; }
	 */
	
	
	public boolean login(String email, String password) {
		Customer cus = cusRepo.findCustomerByEmail(email);
		if(cus.getPassword().equals(password)) {
			loggedCustomerId = cus.getCustomerId();
			return true;
		}
		else return false;
	}
	
	
	// Returns the logged-in Customer object. In case you need it.
	public Customer getLoggedCustomer() {
		return cusRepo.findById(loggedCustomerId).get();
	}
	
	/*
	 * Coupon getter methods
	 */
	
	
	// Return all coupons purchased by customer
	public Set<Coupon> getAllCustomerCoupons(){
		return cusRepo.findById(loggedCustomerId).get().getCoupons();
	}
	
	
	// Returns all customer coupons from specific category
	public Set<Coupon> getCustomerCouponsByCategory(CategoryType category){
		// Initiate HashSet to be returned
		Set<Coupon> coupons = new HashSet<>();
		
		// Loops through all customer coupons and adds ones with matching category to coupons.
		for(Coupon coup : getAllCustomerCoupons()) {
			if(coup.getCategory().equals(category)) coupons.add(coup);
		}
		
		return coupons;
	}
	
	
	// Returns all customer coupons up to specific price
	public Set<Coupon> getCustomerCouponsByPrice(double maxPrice){
		// Initiate HashSet to be returned
		Set<Coupon> coupons = new HashSet<>();
		
		// Loops through all customer coupons and adds ones with price lower than maxPrice.
		for(Coupon coup : getAllCustomerCoupons()) {
			if(coup.getPrice() < maxPrice) coupons.add(coup);
		}
		
		return coupons;
	}
	
	
	/*
	 * Coupon set methods
	 */
	
	// Purchase a coupon
	public void purchaseCoupon(Coupon coupon) throws CouponAlreadyExistsException, CouponExpiredOrNoStockException {
		// Checks that the customer didn't already buy this coupon.
		for(Coupon coup : getAllCustomerCoupons()) {
			// Only checks ID, so an updated coupon will register as already purchased. Feature, not bug.
			if(coup.getCouponId() == coupon.getCouponId()) throw new CouponAlreadyExistsException();
		}
		
		// Checks that the coupon exists, is in stock and not expired, although all these checks should have happened already.
		if(coupRepo.existsById(coupon.getCouponId()) 
				&& coupon.getAmount() > 0
				&& coupon.getEndDate().after(new Date(Calendar.getInstance().getTimeInMillis()))) {
			// Reduces coupon stock amount by 1.
			coupon.setAmount(coupon.getAmount()-1);
			coupRepo.save(coupon);
			// Save the coupon to customer.
			Customer cust = getLoggedCustomer();
			cust.getCoupons().add(coupon);
			cusRepo.save(cust);
			
		}
		else throw new CouponExpiredOrNoStockException();
	}

}