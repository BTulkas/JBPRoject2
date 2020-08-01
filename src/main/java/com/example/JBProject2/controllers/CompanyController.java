package com.example.JBProject2.controllers;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.JBProject2.beans.CategoryType;
import com.example.JBProject2.beans.Coupon;
import com.example.JBProject2.beans.Session;
import com.example.JBProject2.controllers.exceptions.AccessDeniedException;
import com.example.JBProject2.controllers.exceptions.LoginExpiredException;
import com.example.JBProject2.facades.ClientFacade;
import com.example.JBProject2.facades.CompanyFacade;
import com.example.JBProject2.facades.exceptions.CouponAlreadyExistsException;
import com.example.JBProject2.facades.exceptions.CouponNotFoundException;

@RestController
@RequestMapping("company")
@CrossOrigin(origins="http://localhost:4200")
public class CompanyController {
	
	@Autowired
	CompanyFacade compFace;
	
	@Autowired
	private Map<String, Session> sessions;
	
	
	private CompanyFacade checkFacade(String token) throws AccessDeniedException, LoginExpiredException {
		ClientFacade loggedCompany = sessions.get(token).getFacade();
		Session lastAction = sessions.get(token);
		
		if(!(loggedCompany instanceof CompanyFacade))
			throw new AccessDeniedException();
		if(System.currentTimeMillis() - lastAction.getLastActive() > 1000*60*1) {
			throw new LoginExpiredException();
		}
		
		lastAction.setLastActive(System.currentTimeMillis());
		return (CompanyFacade)loggedCompany;
	}
	
	
	@GetMapping("{token}")
	public ResponseEntity<?> getLoggedCompany(@PathVariable String token) throws AccessDeniedException, LoginExpiredException {
		CompanyFacade loggedComp = checkFacade(token);
		return ResponseEntity.ok(loggedComp.getLoggedCompany());
	}
	
	@GetMapping("custsfromcoupon/{token}/{coupId}")
	public ResponseEntity<?> getCustsFromCoupon(@PathVariable String token, @PathVariable int coupId) throws AccessDeniedException, CouponNotFoundException, LoginExpiredException {
		CompanyFacade loggedComp = checkFacade(token);
		return ResponseEntity.ok(loggedComp.getOneCoupon(coupId).getPurchasedBy());
	}
	
	@GetMapping("{token}/coupons")
	public Set<Coupon> getCompanyCoupons(@PathVariable String token) throws AccessDeniedException, LoginExpiredException{
		CompanyFacade loggedComp = checkFacade(token);
		return loggedComp.getCompanyCoupons();
	}
	
	@GetMapping("{token}/coupon/{coupId}")
	public ResponseEntity<?> getOneCoupon(@PathVariable String token, @PathVariable int coupId) throws CouponNotFoundException, AccessDeniedException, LoginExpiredException{
		CompanyFacade loggedComp = checkFacade(token);
		return ResponseEntity.ok(loggedComp.getOneCoupon(coupId));
	}
	
	@GetMapping("{token}/category/{category}")
	public Set<Coupon> getCouponsByCategory(@PathVariable String token, @PathVariable CategoryType category) throws AccessDeniedException, LoginExpiredException{
		CompanyFacade loggedComp = checkFacade(token);
		return (loggedComp).getCouponsByCategory(category);
	}
	
	@GetMapping("{token}/price/{maxPrice}")
	public Set<Coupon> getCouponsByPrice(@PathVariable String token, @PathVariable double maxPrice) throws AccessDeniedException, LoginExpiredException{
		CompanyFacade loggedComp = checkFacade(token);
		return loggedComp.getCouponsByPrice(maxPrice);
	}
	
	@PostMapping("{token}")
	public ResponseEntity<?> addCoupon(@PathVariable String token, @RequestBody Coupon coupon) throws CouponAlreadyExistsException, AccessDeniedException, LoginExpiredException{
		CompanyFacade loggedComp = checkFacade(token);
		return ResponseEntity.ok(loggedComp.addCoupon(coupon));
	}
	
	@PutMapping("{token}/coupon/update")
	public ResponseEntity<?> updateCoupon(@PathVariable String token, @RequestBody Coupon coupon) throws CouponNotFoundException, AccessDeniedException, LoginExpiredException{
		CompanyFacade loggedComp = checkFacade(token);
		return ResponseEntity.ok(loggedComp.updateCoupon(coupon));
	}
	
	@DeleteMapping("{token}/coupon/delete/{coupId}")
	public ResponseEntity<?> deleteCoupon(@PathVariable String token, @PathVariable int coupId) throws CouponNotFoundException, AccessDeniedException, LoginExpiredException {
		CompanyFacade loggedComp = checkFacade(token);
		Coupon coupon = loggedComp.getOneCoupon(coupId);
		loggedComp.deleteCoupon(coupon);
		return ResponseEntity.ok("The deed is done.");
	}
	

}
