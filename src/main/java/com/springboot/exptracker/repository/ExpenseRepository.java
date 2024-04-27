package com.springboot.exptracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.exptracker.entity.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long>{

}
