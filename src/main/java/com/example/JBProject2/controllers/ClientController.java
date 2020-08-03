package com.example.JBProject2.controllers;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.example.JBProject2.beans.Coupon;
import com.example.JBProject2.beans.Session;
import com.example.JBProject2.facades.ClientFacade;
import com.example.JBProject2.facades.GenericFacade;
import com.example.JBProject2.facades.exceptions.CouponNotFoundException;
import com.example.JBProject2.facades.exceptions.CustomerNotFoundException;
import com.example.JBProject2.login_manager.ClientType;
import com.example.JBProject2.login_manager.LoginManager;
import com.example.JBProject2.login_manager.exception.WrongLoginException;

@RestController
@RequestMapping("")
@CrossOrigin(origins="http://localhost:4200")
public class ClientController {
	
	// Login controller + GenericFacade controller 
	
	@Autowired
	GenericFacade genFace;
	
	@Autowired
	LoginManager logMan;
	
	@Autowired
	Map<String, Session> sessions;
	
	// GenericFacade methods
	@GetMapping("client")
	public List<Coupon> getAllCoupons(){
		return genFace.getAllCoupons();
	}
	
	@GetMapping("compfromcoup/{coupId}")
	public ResponseEntity<?> getCompanyFromCoupon(@PathVariable int coupId) throws CouponNotFoundException{
		return ResponseEntity.ok(genFace.getCouponCompany(coupId));
	}
	
	
	
	// Login methods
	@PostMapping("login/{email}/{password}/{clientType}")
    public ResponseEntity<?> login(@PathVariable String email, @PathVariable String password, @PathVariable String clientType) throws WrongLoginException, CustomerNotFoundException {
		// Creates unique token
		String token = UUID.randomUUID().toString();
		ClientFacade loggedCustomer = logMan.login(email, password, ClientType.valueOf(clientType));
		if(loggedCustomer instanceof ClientFacade) {
			Session session = new Session(loggedCustomer, System.currentTimeMillis());
			sessions.put(token, session);
			System.out.println(sessions);
			return ResponseEntity.ok(token);
		} else throw new CustomerNotFoundException();

	}
	
	
	@PostMapping("logout")
	public void logout(@RequestBody String token){
		sessions.remove(token);
	}

}
