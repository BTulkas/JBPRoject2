package com.example.JBProject2.test;

import java.sql.Date;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.JBProject2.beans.CategoryType;
import com.example.JBProject2.beans.Company;
import com.example.JBProject2.beans.Coupon;
import com.example.JBProject2.beans.Customer;
import com.example.JBProject2.db.CouponRepository;
import com.example.JBProject2.facades.AdminFacade;
import com.example.JBProject2.facades.CompanyFacade;
import com.example.JBProject2.facades.CustomerFacade;
import com.example.JBProject2.facades.exceptions.CompanyAlreadyExistsException;
import com.example.JBProject2.facades.exceptions.CompanyNotFoundException;
import com.example.JBProject2.facades.exceptions.CouponAlreadyExistsException;
import com.example.JBProject2.facades.exceptions.CouponExpiredOrNoStockException;
import com.example.JBProject2.facades.exceptions.CouponNotFoundException;
import com.example.JBProject2.facades.exceptions.CustomerAlreadyExistsException;
import com.example.JBProject2.facades.exceptions.CustomerNotFoundException;
import com.example.JBProject2.facades.exceptions.DataMismatchException;
import com.example.JBProject2.login_manager.ClientType;
import com.example.JBProject2.login_manager.LoginManager;
import com.example.JBProject2.login_manager.exception.WrongLoginException;

@Service
public class Test {
	
	@Autowired
	LoginManager logMan;
	
	// Coupon repository autowired because no facade can interact with all coupons.
	@Autowired
	CouponRepository coupRepo;
	
	public void createDatabase() throws WrongLoginException, CompanyAlreadyExistsException, CouponAlreadyExistsException, CustomerAlreadyExistsException, CouponExpiredOrNoStockException, CustomerNotFoundException, CompanyNotFoundException, CouponNotFoundException {
		AdminFacade loggedAdmin = (AdminFacade) logMan.login("admin@admin.co.il", "admin", ClientType.Administrator);
		
		// Company generator.
		for(int i=1; i<=5; i++) {
			loggedAdmin.addCompany(new Company("Comp"+i, "comp"+i+"@compmail.comp", "123"));
		}
		
		// Customer generator.
		for(int i=1; i<=10; i++) {
			loggedAdmin.addCustomer(new Customer("Customer"+i, "Customsonovich", "cust"+i+"@cust.omer", "123"));
		}
		
		
		CompanyFacade loggedCompany = (CompanyFacade) logMan.login("comp1@compmail.comp", "123", ClientType.Company);
		CompanyFacade loggedCompany2 = (CompanyFacade) logMan.login("comp2@compmail.comp", "123", ClientType.Company);
		
		// Coupon generator.
		Calendar startDate = Calendar.getInstance();
		Calendar endDate = Calendar.getInstance();
		endDate.set(2021, Calendar.APRIL, 18);
		
		for(int i=1; i<=10; i++) {
			loggedCompany.addCoupon(new Coupon(loggedCompany.getLoggedCompany(), 
					CategoryType.values()[(int)(Math.random()*5)], 
					"Coupon"+i,
					"A description", 
					new Date(startDate.getTimeInMillis()), 
					new Date(endDate.getTimeInMillis()), 
					(int)(1+Math.random()*100), 
					1+Math.random()*50, 
					"image"));
		}

		for(int i=1; i<=10; i++) {
			loggedCompany2.addCoupon(new Coupon(loggedCompany2.getLoggedCompany(), 
					CategoryType.values()[(int)(Math.random()*5)], 
					"Coupon"+i,
					"A description", 
					new Date(startDate.getTimeInMillis()), 
					new Date(endDate.getTimeInMillis()), 
					(int)(1+Math.random()*100), 
					1+Math.random()*50, 
					"image"));
		}
		
		// Pre-purchase amount.
		System.out.println("Pre purchase coup 1 amount: " + coupRepo.findById(1).get().getAmount());
		
		
		CustomerFacade loggedCustomer = (CustomerFacade)  logMan.login("cust1@cust.omer", "123", ClientType.Customer);
		CustomerFacade loggedCustomer2 = (CustomerFacade)  logMan.login("cust2@cust.omer", "123", ClientType.Customer);
		
		
	    // Capitalist Dream (auto-coupon-buyer). 
		for (Coupon coup : coupRepo.findAll()) {
			if(coup.getCouponId() < 11) 
				loggedCustomer.purchaseCoupon(coup.getCouponId());
			if(coup.getCouponId() >= 5)
				loggedCustomer2.purchaseCoupon(coup.getCouponId());
			 }
		
		// Post-purchase amount.
		System.out.println("Post purchase coup 1 amount: " + coupRepo.findById(1).get().getAmount());
		
	}
	
	
	public void testAll() throws WrongLoginException, CompanyAlreadyExistsException, CompanyNotFoundException, CustomerAlreadyExistsException, CustomerNotFoundException, CouponAlreadyExistsException, CouponNotFoundException, CouponExpiredOrNoStockException, DataMismatchException {
		testAdmin();
		testCompany();
		testCustomer();
		testDeletes();
		testDailyJob();
	}

	
	
	public void testAdmin() throws WrongLoginException, CompanyAlreadyExistsException, CompanyNotFoundException, CustomerAlreadyExistsException, CustomerNotFoundException, CouponAlreadyExistsException, CouponNotFoundException, DataMismatchException{
		
		System.out.println("***************************Admin tests********************************");

		
		AdminFacade loggedAdmin = (AdminFacade) logMan.login("admin@admin.co.il", "admin", ClientType.Administrator);
		System.out.println("Admin login successful.");

		
		/*
		 * Company methods
		 */		
		
		
		// Test getAllCompanies.
		System.out.println("getAllCompanies:" + loggedAdmin.getAllCompanies());
		
		// Test getOneCompany.
		Company comp = loggedAdmin.getOneCompany(2);
		
		// Change the company object to test updateCompany.
		comp.setPassword("onetwothree");
		
		// Test updateCompay.
		loggedAdmin.updateCompany(comp);
		// Print again from database.
		System.out.println("updatedCompany" + loggedAdmin.getOneCompany(2));
		
		
		/*
		 * Customer methods
		 */				

		
		// Test getAllCustomers.
		System.out.println("getAllCustomers: " + loggedAdmin.getAllCustomers());
		
		// Test getOneCustomer.
		Customer cust = loggedAdmin.getOneCustomer(1);
		
		// Change customer object to test updateCustomer.
		cust.setLastName("Newnamestein");
		
		// Test updateCustomer.
		loggedAdmin.updateCustomer(cust);
		// Print again from database.
		System.out.println("updateCustomer: " + loggedAdmin.getOneCustomer(1));
		
	}
	
	
	public void testCompany() throws WrongLoginException, CouponNotFoundException, CompanyNotFoundException, CouponAlreadyExistsException {
		
		System.out.println("***************************Company tests********************************");

		
		CompanyFacade loggedCompany = (CompanyFacade) logMan.login("comp1@compmail.comp", "123", ClientType.Company);
		System.out.println("Company login successful.");
		
		
		// Test getCompanyCoupons.
		System.out.println("getCompanyCoupons: " + loggedCompany.getCompanyCoupons());
		
		// Test getCouponsByCategory.
		CategoryType category = CategoryType.values()[(int)Math.random()*6+1];
		System.out.println("getCouponsByCategory("+category+"):" + loggedCompany.getCouponsByCategory(category));
		
		// Test getCouponsByPrice.
		double price = Math.random()*40+1;
		System.out.println("getCouponsByPrice("+price+"): " + loggedCompany.getCouponsByPrice(price));
		
		// Test getOneCoupon
		Coupon coup = loggedCompany.getOneCoupon(1);
		System.out.println("getOneCoupon: " + coup);
		
		// Change coupon to test updateCoupon.
		coup.setTitle("ChangedCoupon");
		
		// Test updateCoupon.
		loggedCompany.updateCoupon(coup);
		// Print again from database.
		System.out.println("Updated coupon: " + loggedCompany.getOneCoupon(1).getTitle());
		
	}
	
	
	public void testCustomer() throws WrongLoginException, CouponAlreadyExistsException, CouponExpiredOrNoStockException, CustomerNotFoundException {
		
		System.out.println("***************************Customer tests********************************");

		
		CustomerFacade loggedCustomer = (CustomerFacade)  logMan.login("cust1@cust.omer", "123", ClientType.Customer);
		System.out.println("Customer login successful.");
		
		
		// Test getAllCustomerCoupons.
		System.out.println("getAllCustomerCoupons: " + loggedCustomer.getAllCustomerCoupons());
		
		// Test getCustomerCouponsByCagetory.
		System.out.println("getCustomerCouponsByCategory: " + loggedCustomer.getCustomerCouponsByCategory(CategoryType.values()[(int)Math.random()*5]));
		
		// Test getCustomerCouponsByPrice.
		double maxPrice = Math.random()*50+1;
		System.out.println("getCustomerCouponsByPrice("+maxPrice+"): " + loggedCustomer.getCustomerCouponsByPrice(maxPrice));
		
	}
	
	
	public void testDeletes() throws WrongLoginException, CouponNotFoundException, CustomerNotFoundException, CompanyNotFoundException {
		
		System.out.println("***************************Delete tests********************************");
		
		CompanyFacade loggedCompany = (CompanyFacade) logMan.login("comp1@compmail.comp", "123", ClientType.Company);
		CustomerFacade loggedCustomer = (CustomerFacade)  logMan.login("cust1@cust.omer", "123", ClientType.Customer);
		CustomerFacade loggedCustomer2 = (CustomerFacade)  logMan.login("cust2@cust.omer", "123", ClientType.Customer);
		AdminFacade loggedAdmin = (AdminFacade) logMan.login("admin@admin.co.il", "admin", ClientType.Administrator);

		
		// Test deleteCoupon.
		loggedCompany.deleteCoupon(loggedCompany.getOneCoupon(1));
		System.out.println("All coupons, should not contain coupon 1: " + coupRepo.findAll());
		
		// Check that coupon is removed from customer purchases.
		System.out.println("Customer 1 coupons, should not have coupon 1: " + loggedCustomer.getAllCustomerCoupons());
		
		// Test deleteCustomer.
		loggedAdmin.deleteCustomer(1);
		System.out.println("All customers, should not contain customer 1: " + loggedAdmin.getAllCustomers());
		  
		 // Check that customer is removed from coupon purchasedBy.
		 System.out.println("PurchasedBy of coupon 2, should not have customer 1: " + loggedCompany.getOneCoupon(2).getPurchasedBy());
		  
		 // Test deleteCompany
		 loggedAdmin.deleteCompany(1);
		 System.out.println("All companies, should not contain company 1: " + loggedAdmin.getAllCompanies());
		  
		 // Check that company coupons are deleted.
		 System.out.println("All coupons, should not contain company 1 coupons: " + coupRepo.findAll());
		  
		 // Double check coupons in Customer objects
		 System.out.println("All customer 2 coupons, should not contain company 1 coupons: " + loggedCustomer2.getAllCustomerCoupons());
		 
		
	}
	
	
	// Cannot be tested along with testDelete.
	// Deletion must be confirmed manually on Workbench. Can't print something that doesn't exist anymore.
	public void testDailyJob() {
		
		Calendar cal = Calendar.getInstance();
		cal.set(2020, Calendar.MARCH, 19);
				
		Coupon coup = coupRepo.findById(11).get();
		coup.setEndDate(new Date(cal.getTimeInMillis()));
		coupRepo.save(coup);
	}
	

}
