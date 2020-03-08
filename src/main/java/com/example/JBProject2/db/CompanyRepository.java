package com.example.JBProject2.db;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.JBProject2.beans.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
	

}
