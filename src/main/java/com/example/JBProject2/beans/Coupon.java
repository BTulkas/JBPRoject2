package com.example.JBProject2.beans;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="coupons")
public class Coupon {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int couponId;
	
	@ManyToOne
	private Company company;
	
	@Column(nullable=false)
	private CategoryType category;
	
	@Column(nullable=false)
	private String title;
	
	@Column
	private String description;
	
	@Column(nullable=false)
	private Date startDate, endDate;
	
	@Column(nullable=false)
	private int amount;
	
	@Column(nullable=false)
	private double price;
	
	@Column
	private String image;
	
	@ManyToMany(fetch=FetchType.EAGER)
	private List<Customer> purchasedBy;
	
	public Coupon() {}

	public Coupon(Company company, CategoryType category, String title, String description, Date startDate,
			Date endDate, int amount, double price, String image) {
		super();
		this.company = company;
		this.category = category;
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.price = price;
		this.image = image;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public CategoryType getCategory() {
		return category;
	}

	public void setCategory(CategoryType category) {
		this.category = category;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getCouponId() {
		return couponId;
	}
	
	

	public List<Customer> getPurchasedBy() {
		return purchasedBy;
	}

	public void setPurchasedBy(List<Customer> purchasedBy) {
		this.purchasedBy = purchasedBy;
	}

	@Override
	public String toString() {
		return "Coupon [couponId=" + couponId + ", category=" + category + ", title=" + title + ", description="
				+ description + ", startDate=" + startDate + ", endDate=" + endDate + ", amount=" + amount + ", price="
				+ price + ", image=" + image + "]";
	}

	@Override
	public int hashCode() {
		return couponId + title.hashCode() * description.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Coupon) {
			Coupon coup = (Coupon) obj;
			if(coup.couponId == this.couponId && coup.description == this.description) return true;
			else return false;
		} else return false;
	}
	
	
	

}
