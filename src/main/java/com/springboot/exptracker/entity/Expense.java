package com.springboot.exptracker.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="expenses")
public class Expense {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private BigDecimal amount;
	
	@Column(nullable = false)
	private LocalDate expenseDate;
	
	//many to one relationship - Many expenses belongs to one category
	@ManyToOne
	@JoinColumn(name="category_id",nullable = false) //foreign key in expenses table
	private Category category;
	
	public Expense() {
		
	}

	public Expense(Long id, BigDecimal amount, LocalDate expenseDate, Category category) {
		super();
		this.id = id;
		this.amount = amount;
		this.expenseDate = expenseDate;
		this.category = category;
	}

	public Long getIdLong() {
		return id;
	}

	public void setIdLong(Long id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public LocalDate getExpenseDate() {
		return expenseDate;
	}

	public void setExpenseDate(LocalDate expenseDate) {
		this.expenseDate = expenseDate;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	
}
