package com.example.JBProject2.facades;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
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
@Scope("prototype")
public class AdminFacade extends ClientFacade {
	
	@Autowired
	private CompanyRepository compRepo;
	@Autowired
	private CouponRepository coupRepo;
	@Autowired
	private CustomerRepository custRepo;
	
	/*
	 * Company getter methods
	 */
	
	public boolean login(String email, String password) {
		if(email.equals("admin@admin.co.il") && password.equals("admin")) return true;
		else return false;
	}
	
	// Returns all companies 
	public List<Company> getAllCompanies() {

		return compRepo.findAll();
	}
	
	
	// Returns one company by ID
	public Company getOneCompany(int compId) throws CompanyNotFoundException {

		return compRepo.findById(compId).orElseThrow(CompanyNotFoundException::new);
	}
	
	
	/*
	 * Company setter methods
	 */
	
	// Add company to DB
	public Company addCompany(Company company) throws CompanyAlreadyExistsException {
		// Checks for duplicate name or email. Calls findAll instead of getAllCompanies to avoid redundant logic
		if(compRepo.findCompanyByEmail(company.getEmail()).isPresent() || compRepo.findCompanyByName(company.getName()).isPresent())
			throw new CompanyAlreadyExistsException();
		
		compRepo.save(company);
		return company;
	}
	
	
	// Updates a company. Name and ID update will be blocked client-side.
	public Company updateCompany(Company company) throws CompanyNotFoundException, CompanyAlreadyExistsException {
		// Checks that the object to update exists in the DB.
		compRepo.findById(company.getCompanyId()).orElseThrow(CompanyNotFoundException::new);
		if(compRepo.findCompanyByName(company.getName()).isPresent())
			throw new CompanyAlreadyExistsException();
		compRepo.save(company);
		return company;

	}
	
		
	// Deletes a company by ID.
	public void deleteCompany(int compId) throws CompanyNotFoundException {
		// Checks that the object to delete exists in the DB.
		Company comp = compRepo.findById(compId).orElseThrow(CompanyNotFoundException::new);
			// Deletes all coupons by that company.
			for(Coupon coup : comp.getCoupons()) {
				// Clears coupon purchase history.
				coup.getPurchasedBy().clear();
				coupRepo.save(coup);
				// Deletes the coupon.
				coupRepo.delete(coup);
			}
		
		compRepo.deleteById(compId);
	}
	
	
	/*
	 * Customer getter methods
	 */
	
	// Returns all customers
	public List<Customer> getAllCustomers(){

		return custRepo.findAll();
	}
	
	
	// Returns one Customer by ID
	public Customer getOneCustomer(int custId) throws CustomerNotFoundException {

		return custRepo.findById(custId).orElseThrow(CustomerNotFoundException::new);
		}
	
	
	/*
	 * Customer setter methods
	 */
	
	// Adds a customer to the DB
	public Customer addCustomer(Customer customer) throws CustomerAlreadyExistsException {
		// Checks for duplicate email
		if(custRepo.findCustomerByEmail(customer.getEmail()).isPresent())
			throw new CustomerAlreadyExistsException();
		custRepo.save(customer);
		return customer;
	}
	
	
	// Updates a customer
	public Customer updateCustomer(Customer customer) throws CustomerNotFoundException, CustomerAlreadyExistsException {
		// Checks that the object to update exists in the DB.
		if(!custRepo.existsById(customer.getCustomerId())) 
			throw new CustomerNotFoundException();
		if(custRepo.findCustomerByEmail(customer.getEmail()).isPresent())
			throw new CustomerAlreadyExistsException();
		custRepo.save(customer);
		return customer;
	}
	
	
	// Deletes a customer by ID
	public void deleteCustomer(int custId) throws CustomerNotFoundException {
		Customer customer = custRepo.findById(custId).orElseThrow(CustomerNotFoundException::new);
		// Detaches coupons from the customer and saves before deleting.
		for(Coupon coup : customer.getCoupons()) {
			coup.getPurchasedBy().remove(customer);
			coupRepo.save(coup);
		}
		custRepo.deleteById(custId);
	}
	
	
}
