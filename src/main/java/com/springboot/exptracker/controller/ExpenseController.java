package com.springboot.exptracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.exptracker.dto.ExpenseDto;
import com.springboot.exptracker.service.ExpenseService;

@RestController
@RequestMapping("/api/expenses") //base URI
public class ExpenseController {
	
	@Autowired
	private ExpenseService expenseService;
	
	//Build create expense REST API
	@PostMapping
	public ResponseEntity<ExpenseDto> createExpense(@RequestBody ExpenseDto expenseDto){
		ExpenseDto savedExpense = expenseService.createExpense(expenseDto);
		return new ResponseEntity<>(savedExpense, HttpStatus.CREATED);
	}
	
	//Build expense By Id REST API
	@GetMapping("{id}")
	public ResponseEntity<ExpenseDto> getExpenseById(@PathVariable("id") Long expenseId){
		ExpenseDto expenseDto = expenseService.getExpenseById(expenseId);
		return ResponseEntity.ok(expenseDto);
	}
	
	//Build get All Expenses REST API
	@GetMapping
	public ResponseEntity<List<ExpenseDto>> getAllExpenses(){
		List<ExpenseDto> expenses = expenseService.getAllExpenses();
		return ResponseEntity.ok(expenses);
	}
	
	//Build update expense REST API
	@PutMapping("{id}")
	public ResponseEntity<ExpenseDto> updateExpense(@PathVariable("id") Long expenseId,@RequestBody ExpenseDto expenseDto){
		
		ExpenseDto updatedExpense = expenseService.updateExpense(expenseId, expenseDto);
		return ResponseEntity.ok(updatedExpense);
	}
	
	//Build delete expense REST API
	@DeleteMapping
	public ResponseEntity<String> deleteExpense(Long expenseId) {
		expenseService.deleteExpense(expenseId);
		return ResponseEntity.ok("expense successfully deleted");
	}
}
