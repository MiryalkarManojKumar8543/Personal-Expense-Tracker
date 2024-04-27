package com.springboot.exptracker.mapper;

import com.springboot.exptracker.dto.CategoryDto;
import com.springboot.exptracker.dto.ExpenseDto;
import com.springboot.exptracker.entity.Category;
import com.springboot.exptracker.entity.Expense;

public class ExpenseMapper {
	
	//Map Expense entity to ExpenseDto
	public static ExpenseDto mapToExpenseDto(Expense expense) {
		return new ExpenseDto(expense.getIdLong(), expense.getAmount(), expense.getExpenseDate(),
				new CategoryDto(
						expense.getCategory().getId(),
						expense.getCategory().getName()
						)
				);
	}
	
	//Map ExpenseDto to Expense Entity
	public static Expense mapToExpense(ExpenseDto expenseDto) {
		Category category = new Category();
		category.setId(expenseDto.categoryDto().id());
		
		return new Expense(
				expenseDto.id(),
				expenseDto.amount(),
				expenseDto.expenseDate(),
				category
				);
	}
}
