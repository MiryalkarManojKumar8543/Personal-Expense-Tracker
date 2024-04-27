package com.springboot.exptracker.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.exptracker.dto.ExpenseDto;
import com.springboot.exptracker.entity.Category;
import com.springboot.exptracker.entity.Expense;
import com.springboot.exptracker.mapper.ExpenseMapper;
import com.springboot.exptracker.repository.CategoryRepository;
import com.springboot.exptracker.repository.ExpenseRepository;
import com.springboot.exptracker.service.ExpenseService;

@Service
public class ExpenseServiceImpl implements ExpenseService{
	
	@Autowired
	private ExpenseRepository expenseRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public ExpenseDto createExpense(ExpenseDto expenseDto) {
		
		//convert ExpenseDto to expense entity
		Expense expense = ExpenseMapper.mapToExpense(expenseDto);
		
		//save expense entity to database
		Expense savedExpense = expenseRepository.save(expense);
		
		//convert saved expense entity into expenseDto
		return ExpenseMapper.mapToExpenseDto(savedExpense);
	}

	@Override
	public ExpenseDto getExpenseById(Long expenseId) {
		
		//get expense entity from the database using expense id
		Expense expense = expenseRepository.findById(expenseId).orElseThrow(()-> new RuntimeException("Expense not found with id "+expenseId));
		
		//convert expense entity to expenseDto
		return ExpenseMapper.mapToExpenseDto(expense);
	}

	@Override
	public List<ExpenseDto> getAllExpenses() {
		
		List<Expense> expenses = expenseRepository.findAll();
		return expenses.stream().map((expense)-> ExpenseMapper.mapToExpenseDto(expense)).collect(Collectors.toList());
	}

	@Override
	public ExpenseDto updateExpense(Long expenseId, ExpenseDto expenseDto) {
		Expense expense = expenseRepository.findById(expenseId).orElseThrow(()-> new RuntimeException("Expense not found with id "+expenseId));
		
		//update expense amount
		expense.setAmount(expenseDto.amount());
		
		//update expense date
		expense.setExpenseDate(expenseDto.expenseDate());
		
		//update category
		if(expenseDto.categoryDto()!=null) {
			
			//get the category entity by id
			Category category = categoryRepository.findById(expenseDto.categoryDto().id()).orElseThrow(()-> new RuntimeException("Category not found with id "+expenseDto.categoryDto().id()));
			expense.setCategory(category);
		}
		
		//update expense entity into database
		Expense updatedExpense = expenseRepository.save(expense);
		
		//convert expense entity into ExpenseDto
		return ExpenseMapper.mapToExpenseDto(updatedExpense);
	}

	@Override
	public void deleteExpense(Long expenseId) {
		
		Expense expense = expenseRepository.findById(expenseId).orElseThrow(()-> new RuntimeException("Expense not found with id "+expenseId));
		expenseRepository.delete(expense);
		
	}

}
