package com.example.JBProject2.db;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.JBProject2.beans.CategoryType;
import com.example.JBProject2.beans.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Integer> {
	
	public Set<Coupon> findByCategory(CategoryType category);

}
