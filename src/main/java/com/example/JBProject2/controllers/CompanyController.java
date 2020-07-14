package com.example.JBProject2.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;import org.springframework.http.HttpStatus;
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
import org.springframework.web.servlet.view.RedirectView;

import com.example.JBProject2.beans.CategoryType;
import com.example.JBProject2.beans.Coupon;
import com.example.JBProject2.facades.CompanyFacade;
import com.example.JBProject2.facades.exceptions.CouponAlreadyExistsException;
import com.example.JBProject2.facades.exceptions.CouponNotFoundException;

@RestController
@RequestMapping("company")
@CrossOrigin(origins="http://localhost:4200")
public class CompanyController {
	
	@Autowired
	CompanyFacade compFace;
	
	
	@GetMapping("coupons")
	public Set<Coupon> getCompanyCoupons(){
		return compFace.getCompanyCoupons();
	}
	
	@GetMapping("coupon/{coupId}")
	public ResponseEntity<?> getOneCoupon(@PathVariable int coupId) throws CouponNotFoundException{
		return ResponseEntity.ok(compFace.getOneCoupon(coupId));
	}
	
	@GetMapping("category/{category}")
	public Set<Coupon> getCouponsByCategory(@PathVariable CategoryType category){
		return compFace.getCouponsByCategory(category);
	}
	
	@GetMapping("price/{price}")
	public Set<Coupon> getCouponsByPrice(@PathVariable double maxPrice){
		return compFace.getCouponsByPrice(maxPrice);
	}
	
	@PostMapping
	public ResponseEntity<?> addCoupon(@RequestBody Coupon coupon) throws CouponAlreadyExistsException{
		return ResponseEntity.ok(compFace.addCoupon(coupon));
	}
	
	@PutMapping("coupon/update")
	public ResponseEntity<?> updateCoupon(@RequestBody Coupon coupon) throws CouponNotFoundException{
		return ResponseEntity.ok(compFace.updateCoupon(coupon));
	}
	
	@DeleteMapping("coupon/delete")
	public RedirectView deleteCoupon(@RequestBody Coupon coupon) throws CouponNotFoundException {
		compFace.deleteCoupon(coupon);
		return new RedirectView("company/coupons");
	}
	

}
