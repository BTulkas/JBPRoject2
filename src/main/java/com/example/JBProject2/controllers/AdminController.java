package com.example.JBProject2.controllers;

import java.util.List;
import java.util.Map;

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
import org.springframework.web.servlet.view.RedirectView;

import com.example.JBProject2.beans.Company;
import com.example.JBProject2.beans.Customer;
import com.example.JBProject2.beans.Session;
import com.example.JBProject2.controllers.exceptions.AccessDeniedException;
import com.example.JBProject2.controllers.exceptions.LoginExpiredException;
import com.example.JBProject2.facades.AdminFacade;
import com.example.JBProject2.facades.ClientFacade;
import com.example.JBProject2.facades.exceptions.CompanyAlreadyExistsException;
import com.example.JBProject2.facades.exceptions.CompanyNotFoundException;
import com.example.JBProject2.facades.exceptions.CustomerAlreadyExistsException;
import com.example.JBProject2.facades.exceptions.CustomerNotFoundException;

@RestController
@RequestMapping("admin")
@CrossOrigin(origins="http://localhost:4200")
public class AdminController {
	
	@Autowired
	private AdminFacade adFace;
	
	@Autowired
	private Map<String, Session> sessions;
	
	private AdminFacade checkFacade(String token) throws AccessDeniedException, LoginExpiredException {
		ClientFacade loggedAdmin = sessions.get(token).getFacade();
		Session lastAction = sessions.get(token);
		
		if(!(loggedAdmin instanceof AdminFacade))
			throw new AccessDeniedException();
		if(System.currentTimeMillis() - lastAction.getLastActive() > 1000*60*1) {
			throw new LoginExpiredException();
		}
		
		lastAction.setLastActive(System.currentTimeMillis());
		return (AdminFacade)loggedAdmin;
	}

	/*
	 * Company mappings
	 */
	
	@GetMapping("companies/{token}")
	public List<Company> getAllCompanies(@PathVariable String token) throws AccessDeniedException, LoginExpiredException{
		AdminFacade loggedAdmin = checkFacade(token); 
		return loggedAdmin.getAllCompanies();
	}
	
	@GetMapping("company/{token}/{compId}")
	public ResponseEntity<?> getOneCompany(@PathVariable String token, @PathVariable int compId) throws CompanyNotFoundException, AccessDeniedException, LoginExpiredException{
		AdminFacade loggedAdmin = checkFacade(token); 
		return ResponseEntity.ok(((AdminFacade) loggedAdmin).getOneCompany(compId));
	}
	
	@PostMapping("companies/{token}")
	public ResponseEntity<?> addCompany(@PathVariable String token, @RequestBody Company company) throws CompanyAlreadyExistsException, AccessDeniedException, LoginExpiredException{
		AdminFacade loggedAdmin = checkFacade(token); 
		return ResponseEntity.ok(loggedAdmin.addCompany(company));
	}
	
	@PutMapping("company/update/{token}")
	public ResponseEntity<?> updateCompany(@PathVariable String token, @RequestBody Company company) throws CompanyNotFoundException, AccessDeniedException, LoginExpiredException{
		AdminFacade loggedAdmin = checkFacade(token); 
		return ResponseEntity.ok(loggedAdmin.updateCompany(company));
	}
	
	 @DeleteMapping("company/delete/{token}/{compId}")
	 public ResponseEntity<?> deleteCompany(@PathVariable String token, @PathVariable int compId) throws CompanyNotFoundException, AccessDeniedException, LoginExpiredException{
		 AdminFacade loggedAdmin = checkFacade(token);
		 loggedAdmin.deleteCompany(compId);
		 return ResponseEntity.ok("Successfully increased unemployment");
	 }
	 
	 
	/*
	 * Customer mappings
	 */
	 
	 @GetMapping("customers/{token}")
	 public List<Customer> getAllCustomers(@PathVariable String token) throws AccessDeniedException, LoginExpiredException{
		 AdminFacade loggedAdmin = checkFacade(token);
		 return loggedAdmin.getAllCustomers();
	 }
	 
	 @GetMapping("customer/{token}/{custId}")
	 public ResponseEntity<?> getOneCustomer(@PathVariable String token, @PathVariable int custId) throws CustomerNotFoundException, AccessDeniedException, LoginExpiredException {
		 AdminFacade loggedAdmin = checkFacade(token);
		 return ResponseEntity.ok(loggedAdmin.getOneCustomer(custId));
	 }
	 
	 @PostMapping("customers/{token}")
	 public ResponseEntity<?> addCustomer(@PathVariable String token, @RequestBody Customer customer) throws CustomerAlreadyExistsException, AccessDeniedException, LoginExpiredException{
		 AdminFacade loggedAdmin = checkFacade(token);
		 return ResponseEntity.ok(loggedAdmin.addCustomer(customer));
	 }
	 
	 @PutMapping("customer/update/{token}")
	 public ResponseEntity<?> updateCustomer(@PathVariable String token, @RequestBody Customer customer) throws CustomerNotFoundException, AccessDeniedException, LoginExpiredException{
		 AdminFacade loggedAdmin = checkFacade(token);
		 return ResponseEntity.ok(loggedAdmin.updateCustomer(customer));
	 }
	 
	 @DeleteMapping("customer/delete/{token}/{custId}")
	 public ResponseEntity<?> deleteCustomer(@PathVariable String token, @PathVariable int custId) throws CustomerNotFoundException, AccessDeniedException, LoginExpiredException {
		 AdminFacade loggedAdmin = checkFacade(token);
		 loggedAdmin.deleteCustomer(custId);
		 return ResponseEntity.ok("Customer gone");
	 }

}
