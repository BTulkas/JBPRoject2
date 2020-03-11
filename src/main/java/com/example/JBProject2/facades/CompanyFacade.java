package com.example.JBProject2.facades;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.JBProject2.beans.CategoryType;
import com.example.JBProject2.beans.Company;
import com.example.JBProject2.beans.Coupon;
import com.example.JBProject2.db.CompanyRepository;
import com.example.JBProject2.db.CouponRepository;
import com.example.JBProject2.facades.exceptions.CouponAlreadyExistsException;
import com.example.JBProject2.facades.exceptions.CouponNotFoundException;

@Service
public class CompanyFacade {
	
	private int loggedCompanyId;
	
	@Autowired
	CouponRepository coupRepo;
	@Autowired
	CompanyRepository compRepo;
	
	
	public boolean login(String email, String password) {
		Company comp = compRepo.findCompanyByEmail(email);
		if(compRepo.existsById(comp.getCompanyId())) {
			loggedCompanyId = comp.getCompanyId();
			return true;
		}
		else return false;
	}
	
	/*
	 * Coupon getter methods
	 */	
	
	// Returns all coupons by logged company
	public Set<Coupon> getCompanyCoupons(){
		// Initiates set to return.
		HashSet<Coupon> coupons = new HashSet<>();
		// Adds coupons with correct company to the set.
		for(Coupon coup : coupRepo.findAll()) {
			if(coup.getCompany().getCompanyId() == loggedCompanyId) coupons.add(coup);
		}
		return coupons;
	}
	
	
	// Returns one coupon by ID
	public Coupon getOneCoupon(int coupId) throws CouponNotFoundException {
		if(coupRepo.findById(coupId).isPresent() && coupRepo.findById(coupId).get() instanceof Coupon)
			return coupRepo.findById(coupId).get();
		else throw new CouponNotFoundException();
	}
	
	
	// Returns all company coupons by category
	public Set<Coupon> getCouponsByCategory(CategoryType category){
		// Initiates set to return.
		HashSet<Coupon> coupons = new HashSet<>();
		// Adds coupons with correct company and category to the set.
		for(Coupon coup : getCompanyCoupons()) {
			if(coup.getCategory().equals(category)) coupons.add(coup);
		}
		return coupons;
	}
	
	
	// Returns all company coupons by max price
	public Set<Coupon> getCouponsByPrice(double maxPrice){
		// Initiates set to return.
		HashSet<Coupon> coupons = new HashSet<>();
		// Adds coupons with correct company and price to the set.
		for(Coupon coup : getCompanyCoupons()) {
			if(coup.getPrice() < maxPrice) coupons.add(coup);
		}
		return coupons;
	}
	
	
	/*
	 * Coupon setter methods
	 */
	
	// Add a coupon
	public void addCoupon(Coupon coupon) throws CouponAlreadyExistsException {
		// Checks for duplicate titles within the same company
		for(Coupon coup : getCompanyCoupons()) {
			if(coupon.getTitle().equals(coup.getTitle())) throw new CouponAlreadyExistsException();
		}
		coupRepo.save(coupon);
	}
	
	
	// Updates a coupon by Coupon object
	public void updateCoupon(Coupon coupon) throws CouponNotFoundException {
		// Checks the coupon belongs to the company before saving.
		if(coupon.getCompany().getCompanyId() == loggedCompanyId) coupRepo.save(coupon);
		else throw new CouponNotFoundException();
	}
}
