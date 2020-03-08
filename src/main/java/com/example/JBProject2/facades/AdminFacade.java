package com.example.JBProject2.facades;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.JBProject2.beans.Company;
import com.example.JBProject2.beans.Coupon;
import com.example.JBProject2.beans.Customer;
import com.example.JBProject2.db.CompanyRepository;
import com.example.JBProject2.db.CouponRepository;
import com.example.JBProject2.db.CustomerRepository;
import com.example.JBProject2.facades.exceptions.CompanyAlreadyExistsException;
import com.example.JBProject2.facades.exceptions.CompanyNotFoundException;
import com.example.JBProject2.facades.exceptions.CustomerAlreadyExistsException;
import com.example.JBProject2.facades.exceptions.CustomerNotFoundException;

@Service
public class AdminFacade {
	
	@Autowired
	private CompanyRepository compRepo;
	@Autowired
	private CouponRepository coupRepo;
	@Autowired
	private CustomerRepository custRepo;
	
	/*
	 * Company getter methods
	 */
	
	// Returns all companies 
	public Set<Company> getAllCompanies() {
		// Initiate empty Set to ensure no duplicates
		HashSet<Company> companies = new HashSet<Company>();
		
		// Checks that returned objects are Company
		for(Object obj : compRepo.findAll()) {
			if(obj instanceof Company) companies.add((Company)obj);
		}
		
		return companies;
	}
	
	
	// Returns one company by ID
	public Company getOneCompany(int compId) throws CompanyNotFoundException {
		if(compRepo.findById(compId).isPresent() && compRepo.findById(compId).get() instanceof Company) 
			return compRepo.findById(compId).get();
		else throw new CompanyNotFoundException();
	}
	
	
	/*
	 * Company setter methods
	 */
	
	// Add company to DB
	public void addCompany(Company company) throws CompanyAlreadyExistsException {
		// Checks for duplicate name or email. Calls findAll instead of getAllCompanies to avoid redundant logic
		for(Company comp : getAllCompanies()) {
			if(comp.getName().equals(company.getName()) || comp.getEmail().equals(company.getEmail()))
				throw new CompanyAlreadyExistsException();
		}
		compRepo.save(company);
	}
	
	
	// Updates a company. Name and ID update will be blocked client-side.
	public void updateCompany(Company company) throws CompanyNotFoundException {
		// Checks that the object to update exists in the DB and is of correct type.
		if(compRepo.findById(company.getCompanyId()).isPresent() && compRepo.findById(company.getCompanyId()).get() instanceof Company) 
			compRepo.save(company);
		else throw new CompanyNotFoundException();
	}
	
		
	// Deletes a company by ID.
	public void deleteCompany(int compId) throws CompanyNotFoundException {
		// Checks that the object to delete exists in the DB and is of correct type.
		if(compRepo.findById(compId).isPresent() && compRepo.findById(compId).get() instanceof Company) {
			// Deletes all coupons by that company.
			for(Coupon coup : compRepo.findById(compId).get().getCoupons()) {
				coupRepo.delete(coup);
			}
			compRepo.deleteById(compId);
		}
		else throw new CompanyNotFoundException();
	}
	
	
	/*
	 * Customer getter methods
	 */
	
	// Returns all customers
	public Set<Customer> getAllCustomers(){
		// Initiate empty Set to ensure no duplicates
		HashSet<Customer> customers = new HashSet<Customer>();
		
		// Checks that returned objects are Customer
		for(Object obj : custRepo.findAll()) {
			if(obj instanceof Customer) customers.add((Customer)obj);
		}
		return customers;
	}
	
	
	// Returns one Customer by ID
	public Customer getOneCustomer(int custId) throws CustomerNotFoundException {
		if(custRepo.findById(custId).isPresent() && custRepo.findById(custId).get() instanceof Customer) 
			return custRepo.findById(custId).get();
		else throw new CustomerNotFoundException();
		}
	
	
	/*
	 * Customer setter methods
	 */
	
	// Adds a customer to the DB
	public void addCustomer(Customer customer) throws CustomerAlreadyExistsException {
		// Checks for duplicate name or email. Calls findAll instead of getAllCompanies to avoid redundant logic
		for(Customer cust : getAllCustomers()) {
			if(cust.getEmail().equals(customer.getEmail()))
				throw new CustomerAlreadyExistsException();
		}
		custRepo.save(customer);
	}
	
	
	// Updates a customer
	public void updateCustomer(Customer customer) throws CustomerNotFoundException {
		// Checks that the object to update exists in the DB and is of correct type.
		if(custRepo.findById(customer.getCustomerId()).isPresent() && custRepo.findById(customer.getCustomerId()).get() instanceof Customer) 
			custRepo.save(customer);
		else throw new CustomerNotFoundException();
	}
	
	
	// Deletes a customer by ID
	public void deleteCustomer(int custId) throws CustomerNotFoundException {
		if(custRepo.findById(custId).isPresent() && custRepo.findById(custId).get() instanceof Customer) 
			custRepo.deleteById(custId);
		else throw new CustomerNotFoundException();
	}
	
	
}
