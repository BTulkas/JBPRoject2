package com.example.JBProject2.facades;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.example.JBProject2.beans.CategoryType;
import com.example.JBProject2.beans.Company;
import com.example.JBProject2.beans.Coupon;
import com.example.JBProject2.db.CompanyRepository;
import com.example.JBProject2.db.CouponRepository;
import com.example.JBProject2.db.CustomerRepository;
import com.example.JBProject2.facades.exceptions.CouponAlreadyExistsException;
import com.example.JBProject2.facades.exceptions.CouponNotFoundException;
import com.example.JBProject2.login_manager.exception.WrongLoginException;

@Service
@Scope("prototype")
public class CompanyFacade extends ClientFacade {
	
	private int loggedCompanyId;
	
	@Autowired
	CouponRepository coupRepo;
	@Autowired
	CompanyRepository compRepo;
	@Autowired
	CustomerRepository custRepo;
	
	
	public boolean login(String email, String password) throws WrongLoginException {
		Company comp = compRepo.findCompanyByEmail(email).orElseThrow(WrongLoginException::new);
		if(comp.getPassword().equals(password)) {
			// Saves the company ID for use in facade methods.
			loggedCompanyId = comp.getCompanyId();
			return true;
		}
		return false;
	}

	
	// Returns the logged-in Company object.
	public Company getLoggedCompany() {
		return compRepo.findById(loggedCompanyId).get();
	}
	
	
	/*
	 * Coupon getter methods
	 */	
	
	// Returns all coupons by logged company
	public Set<Coupon> getCompanyCoupons(){

		return coupRepo.findByCompany(getLoggedCompany());
	}
	
	
	// Returns one coupon by ID
	public Coupon getOneCoupon(int coupId) throws CouponNotFoundException {

		Coupon coup = coupRepo.findById(coupId).orElseThrow(CouponNotFoundException::new);

		if(coup.getCompany().getCompanyId() != loggedCompanyId)
			throw new CouponNotFoundException();
		return coup;
	}
	
	
	// Returns all company coupons by category
	public Set<Coupon> getCouponsByCategory(CategoryType category){

		return coupRepo.findByCompanyAndCategory(getLoggedCompany(), category);
	}
	
	
	// Returns all company coupons by max price
	public Set<Coupon> getCouponsByPrice(double maxPrice){
	
		return coupRepo.findByCompanyAndPriceLessThanEqual(getLoggedCompany(), maxPrice);
	}
	
	
	/*
	 * Coupon setter methods
	 */
	
	// Add a coupon
	public Coupon addCoupon(Coupon coupon) throws CouponAlreadyExistsException {
		// Checks for duplicate titles within the same company
		  for(Coupon coup : getCompanyCoupons()) {
			  if(coupon.getTitle().equals(coup.getTitle()))
				  throw new CouponAlreadyExistsException();
		  }
		
	    // Connects new coupon to the logged company
	    coupon.setCompany(getLoggedCompany());

		coupRepo.save(coupon);
		return coupon;
	}
	
	
	// Updates a coupon by Coupon object
	public Coupon updateCoupon(Coupon coupon) throws CouponNotFoundException, CouponAlreadyExistsException {
		// Checks the coupon exists and belongs to the company before saving.
		if(!coupRepo.existsById(coupon.getCouponId()) || coupon.getCompany().getCompanyId() != loggedCompanyId)
			throw new CouponNotFoundException();
		if(coupRepo.findByCompanyAndTitle(coupon.getCompany(), coupon.getTitle()))
			throw new CouponAlreadyExistsException();
		
		coupRepo.save(coupon);
		return coupon;
	}
	
	
	// Deletes a coupon and purchase history. On purpose. For some reason.
	public void deleteCoupon(Coupon coupon) throws CouponNotFoundException {
		// Checks the coupon exists and belongs to the company before deleting.
		if(!coupRepo.existsById(coupon.getCouponId()) || coupon.getCompany().getCompanyId() != loggedCompanyId)
			throw new CouponNotFoundException();
			

		// Clears coupon purchases.
		coupon.getPurchasedBy().clear();
		coupRepo.save(coupon);
		// Deletes the coupon.
		coupRepo.delete(coupon);
		
	}
	
	

}
