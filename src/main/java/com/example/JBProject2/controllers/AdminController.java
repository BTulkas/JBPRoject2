package com.example.JBProject2.controllers;

import java.util.List;

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
import com.example.JBProject2.facades.AdminFacade;
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

	/*
	 * Company mappings
	 */
	
	@GetMapping("companies")
	public List<Company> getAllCompanies(){
		return adFace.getAllCompanies();
	}
	
	@GetMapping("company/{compId}")
	public ResponseEntity<?> getOneCompany(@PathVariable int compId) throws CompanyNotFoundException{
		return ResponseEntity.ok(adFace.getOneCompany(compId));
	}
	
	@PostMapping("companies")
	public ResponseEntity<?> addCompany(@RequestBody Company company) throws CompanyAlreadyExistsException{
		return ResponseEntity.ok(adFace.addCompany(company));
	}
	
	@PutMapping("company/update")
	public ResponseEntity<?> updateCompany(@RequestBody Company company) throws CompanyNotFoundException{
		return ResponseEntity.ok(adFace.updateCompany(company));
	}
	
	 @DeleteMapping("company/delete/{compId}")
	 public RedirectView deleteCompany(@PathVariable int compId) throws CompanyNotFoundException{
		 adFace.deleteCompany(compId);
		 return new RedirectView("admin/companies");
	 }
	 
	 
	/*
	 * Customer mappings
	 */
	 
	 @GetMapping("customers")
	 public List<Customer> getAllCustomers(){
		 return adFace.getAllCustomers();
	 }
	 
	 @GetMapping("customer/{custId}")
	 public ResponseEntity<?> getOneCustomer(@PathVariable int custId) throws CompanyNotFoundException{
		 return ResponseEntity.ok(adFace.getOneCompany(custId));
	 }
	 
	 @PostMapping("customers")
	 public ResponseEntity<?> addCustomer(@RequestBody Customer customer) throws CustomerAlreadyExistsException{
		 return ResponseEntity.ok(adFace.addCustomer(customer));
	 }
	 
	 @PutMapping("customer/update")
	 public ResponseEntity<?> updateCustomer(@RequestBody Customer customer) throws CustomerNotFoundException{
		 return ResponseEntity.ok(adFace.updateCustomer(customer));
	 }
	 
	 @DeleteMapping("customer/delete/{custId}")
	 public RedirectView deleteCustomer(@PathVariable int custId) throws CustomerNotFoundException {
		 adFace.deleteCustomer(custId);
		 return new RedirectView("admin/customers");
	 }

}
