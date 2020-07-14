package com.example.JBProject2.controllers;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.example.JBProject2.beans.Coupon;
import com.example.JBProject2.facades.ClientFacade;
import com.example.JBProject2.facades.GenericFacade;
import com.example.JBProject2.login_manager.ClientType;
import com.example.JBProject2.login_manager.LoginManager;
import com.example.JBProject2.login_manager.exception.WrongLoginException;

@RestController
@RequestMapping("")
@CrossOrigin(origins="http://localhost:4200")
public class ClientController {
	
	// Basically just a login controller + get all coupons
	
	@Autowired
	GenericFacade genFace;
	
	@Autowired
	LoginManager logMan;
	
	@Autowired
	Map<String, ClientFacade> sessions;
	
	
	@GetMapping
	public List<Coupon> getAllCoupons(){
		return genFace.getAllCoupons();
	}
	
	@PostMapping("login")
    public ResponseEntity<?> login(@RequestBody String email, String password, ClientType clientType) throws WrongLoginException {
		String token = UUID.randomUUID().toString();
		sessions.put(token, logMan.login(email, password, clientType));
		return ResponseEntity.ok(token);

	}
	
	@PostMapping("logout")
	public RedirectView logout(@RequestBody String token){
		sessions.remove(token);
		return new RedirectView("");
	}

}
