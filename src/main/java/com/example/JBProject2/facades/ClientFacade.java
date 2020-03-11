package com.example.JBProject2.facades;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.JBProject2.db.CompanyRepository;
import com.example.JBProject2.db.CouponRepository;
import com.example.JBProject2.db.CustomerRepository;

public interface ClientFacade {
		
	public abstract boolean login(String email, String password);

}
