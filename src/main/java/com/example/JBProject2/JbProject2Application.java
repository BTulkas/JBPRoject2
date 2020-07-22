package com.example.JBProject2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.example.JBProject2.facades.exceptions.CompanyAlreadyExistsException;
import com.example.JBProject2.facades.exceptions.CompanyNotFoundException;
import com.example.JBProject2.facades.exceptions.CouponAlreadyExistsException;
import com.example.JBProject2.facades.exceptions.CouponExpiredOrNoStockException;
import com.example.JBProject2.facades.exceptions.CouponNotFoundException;
import com.example.JBProject2.facades.exceptions.CustomerAlreadyExistsException;
import com.example.JBProject2.facades.exceptions.CustomerNotFoundException;
import com.example.JBProject2.facades.exceptions.DataMismatchException;
import com.example.JBProject2.login_manager.CouponExpirationDailyJob;
import com.example.JBProject2.login_manager.exception.WrongLoginException;
import com.example.JBProject2.test.Test;

@SpringBootApplication
public class JbProject2Application {

	public static void main(String[] args) {
		//ConfigurableApplicationContext ctx = 
				SpringApplication.run(JbProject2Application.class, args);
		
		//Test test = ctx.getBean(Test.class);
		
		//CouponExpirationDailyJob dailyJob = ctx.getBean(CouponExpirationDailyJob.class);
		
		
		/*
		 * try { test.createDatabase(); } catch (WrongLoginException |
		 * CompanyAlreadyExistsException | CouponAlreadyExistsException |
		 * CustomerAlreadyExistsException | CouponExpiredOrNoStockException |
		 * CustomerNotFoundException | CompanyNotFoundException e) {
		 * System.out.println(e.getMessage()); }
		 * 
		 * 
		 * 
		 * 
		 * try { test.testAll(); dailyJob.start();} catch (WrongLoginException |
		 * CompanyAlreadyExistsException | CompanyNotFoundException |
		 * CustomerAlreadyExistsException | CustomerNotFoundException |
		 * CouponAlreadyExistsException | CouponNotFoundException |
		 * CouponExpiredOrNoStockException | DataMismatchException e) {
		 * System.out.println(e.getMessage()); } finally { dailyJob.quit(); }
		 */
		 

		
		
		/*
		 * try { test.testCustomer(); } catch (WrongLoginException |
		 * CouponAlreadyExistsException | CouponExpiredOrNoStockException e) {
		 * System.out.println(e.getMessage());; }
		 */
		 		 

		
		
		/*
		 * try { test.testDeletes(); } catch (WrongLoginException |
		 * CouponNotFoundException | CustomerNotFoundException |
		 * CompanyNotFoundException e) { System.out.println(e.getMessage()); }
		 */
		 
		 
		
	}
	

}
