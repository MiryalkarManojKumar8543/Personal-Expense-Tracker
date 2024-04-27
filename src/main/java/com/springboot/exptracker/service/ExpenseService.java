package com.springboot.exptracker.service;

import java.util.List;

import com.springboot.exptracker.dto.ExpenseDto;

public interface ExpenseService {
	
	ExpenseDto createExpense(ExpenseDto expenseDto);
	
	ExpenseDto getExpenseById(Long expenseId);
	
	List<ExpenseDto> getAllExpenses();
	
	ExpenseDto updateExpense(Long expenseId, ExpenseDto expenseDto);
	
	void deleteExpense(Long expenseId);
}
