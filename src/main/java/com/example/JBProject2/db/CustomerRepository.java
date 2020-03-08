package com.example.JBProject2.db;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.JBProject2.beans.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
