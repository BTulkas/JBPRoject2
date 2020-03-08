package com.example.JBProject2.db;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.JBProject2.beans.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Integer> {

}
