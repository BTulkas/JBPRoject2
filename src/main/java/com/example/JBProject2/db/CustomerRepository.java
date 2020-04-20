package com.example.JBProject2.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.JBProject2.beans.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	public Customer findCustomerByEmail(String email);

}
