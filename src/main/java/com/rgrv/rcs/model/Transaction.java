package com.rgrv.rcs.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Transaction {

	@Id
	private Integer id;
	@ManyToOne
    @JoinColumn(name="customer_id", nullable=false)
	private Customer customer;
	@Column
	private Date date;
	@Column
	private Double amount;
	public Transaction() {
	}
	public Transaction(Customer customer, Integer id, Date date, Double amount) {
		setCustomer(customer);
		setId(id);
		setDate(date);
		setAmount(amount);
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
}
