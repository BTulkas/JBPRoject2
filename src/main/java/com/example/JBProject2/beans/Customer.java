package com.example.JBProject2.beans;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="customers")
public class Customer {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int customerId;
	
	@Column(nullable=false)
	private String firstName, lastName, email, password;
	
	@ManyToMany(mappedBy="purchasedBy", fetch = FetchType.EAGER)
	private List<Coupon> coupons;
	
	
	public Customer() {}


	public Customer(String firstName, String lastName, String email, String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public List<Coupon> getCoupons() {
		return coupons;
	}


	public void setCoupons(List<Coupon> coupons) {
		this.coupons = coupons;
	}


	public int getCustomerId() {
		return customerId;
	}


	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", firstName=" + firstName + ", lastName=" + lastName + ", email="
				+ email + ", password=" + password + ", coupons=" + coupons + "]";
	}


	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return customerId + firstName.hashCode()*lastName.hashCode();
	}


	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Customer) {
			Customer cust = (Customer) obj;
			if(cust.customerId == this.customerId && cust.firstName == this.firstName) return true;
			else return false;
		} else return false;
	}
	
	
	
	
	

}
