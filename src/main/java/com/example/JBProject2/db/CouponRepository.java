package com.example.JBProject2.db;

import java.sql.Date;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.JBProject2.beans.CategoryType;
import com.example.JBProject2.beans.Company;
import com.example.JBProject2.beans.Coupon;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Integer> {
	
	public Set<Coupon> findByCompany(Company company);
	public Set<Coupon> findByCompanyAndPriceLessThanEqual(Company company, double maxPrice);
	public Set<Coupon> findByCompanyAndCategory(Company company, CategoryType category);
	public boolean findByCompanyAndTitle(Company company, String title);
	public Set<Coupon> findByEndDateBefore(Date date);
}
