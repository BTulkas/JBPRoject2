package com.example.JBProject2.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.JBProject2.beans.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {
	
	public Company findCompanyByEmail(String email);
	

}
