package com.example.JBProject2.db;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.JBProject2.beans.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {
	
	public Optional<Company> findCompanyByEmail(String email);	
	public Optional<Company> findCompanyByName(String name);	

}
