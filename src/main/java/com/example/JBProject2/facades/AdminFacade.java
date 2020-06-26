package com.example.JBProject2.facades;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	public void addCompany(Company company) throws CompanyAlreadyExistsException {
		// Checks for duplicate name or email. Calls findAll instead of getAllCompanies to avoid redundant logic
		if(compRepo.findCompanyByEmail(company.getEmail()).isEmpty() && compRepo.findCompanyByName(company.getName()).isEmpty())
			compRepo.save(company);
		else throw new CompanyAlreadyExistsException();
	}
	
	
	// Updates a company. Name and ID update will be blocked client-side.
	public void updateCompany(Company company) throws CompanyNotFoundException {
		// Checks that the object to update exists in the DB.
		compRepo.findById(company.getCompanyId()).orElseThrow(CompanyNotFoundException::new);
		compRepo.save(company);

	}
	
		
	// Deletes a company by ID.
	public void deleteCompany(int compId) throws CompanyNotFoundException {
		// Checks that the object to delete exists in the DB.
		Company comp = compRepo.findById(compId).orElseThrow(CompanyNotFoundException::new);
			// Deletes all coupons by that company.
			for(Coupon coup : comp.getCoupons()) {
				// Removes the coupon from customers by reverse query
				for (Customer cust : getAllCustomers()) {
					cust.getCoupons().remove(coup);
					custRepo.save(cust);
				}

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
	public void addCustomer(Customer customer) throws CustomerAlreadyExistsException {
		// Checks for duplicate email
		if(custRepo.findCustomerByEmail(customer.getEmail()).isPresent())
			throw new CustomerAlreadyExistsException();
		custRepo.save(customer);
	}
	
	
	// Updates a customer
	public void updateCustomer(Customer customer) throws CustomerNotFoundException {
		// Checks that the object to update exists in the DB.
		if(!custRepo.existsById(customer.getCustomerId())) 
			throw new CustomerNotFoundException();
		custRepo.save(customer);
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
