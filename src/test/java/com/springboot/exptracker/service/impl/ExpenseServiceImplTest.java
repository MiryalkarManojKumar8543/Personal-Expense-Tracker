package com.springboot.exptracker.service.impl;

import com.springboot.exptracker.dto.ExpenseDto;
import com.springboot.exptracker.entity.Category;
import com.springboot.exptracker.entity.Expense;
import com.springboot.exptracker.mapper.ExpenseMapper;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.springboot.exptracker.repository.CategoryRepository;
import com.springboot.exptracker.repository.ExpenseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExpenseServiceImplTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
    private ExpenseServiceImpl expenseService;

    @Mock
    private CategoryRepository categoryRepository;

    @Test
    void testCreateExpense() {

        Category category = new Category(1L,"food");
        Expense expense = new Expense(1L, BigDecimal.valueOf(789.012), LocalDate.now(),category);
        ExpenseDto expenseDto = ExpenseMapper.mapToExpenseDto(expense);

        when(expenseRepository.save(any(Expense.class))).thenReturn(expense);

        ExpenseDto savedExpense = expenseService.createExpense(expenseDto);

        verify(expenseRepository, times(1)).save(any(Expense.class));

        assertNotNull(savedExpense);
        assertEquals(savedExpense.id(), savedExpense.id());
        assertEquals(savedExpense.amount(), savedExpense.amount());
        assertEquals(savedExpense.expenseDate(), savedExpense.expenseDate());
        assertEquals(savedExpense.categoryDto().id(), savedExpense.categoryDto().id());
        assertEquals(savedExpense.categoryDto().name(), savedExpense.categoryDto().name());
    }


    @Test
    void testGetExpenseById() {
        Long expenseId = 1L;
        Category category = new Category(1L,"travel");
        Expense expense = new Expense(1L, BigDecimal.valueOf(1000.012), LocalDate.now(),category);

        when(expenseRepository.findById(expenseId)).thenReturn(Optional.of(expense));

        ExpenseDto foundExpense = expenseService.getExpenseById(expenseId);

        assertNotNull(foundExpense);
        assertEquals(expense.getIdLong(), foundExpense.id());
        assertEquals(expense.getAmount(), foundExpense.amount());
        assertEquals(expense.getExpenseDate(), foundExpense.expenseDate());
        assertEquals(expense.getCategory().getId(), foundExpense.categoryDto().id());
        assertEquals(expense.getCategory().getName(), foundExpense.categoryDto().name());

    }

    @Test
    void testGetAllExpenses() {
        List<Expense> expenseEntities = Arrays.asList(
                new Expense(1L, new BigDecimal("50.25"), LocalDate.now(), new Category(1L, "Shopping")),
                new Expense(2L, new BigDecimal("75.80"), LocalDate.now(), new Category(2L, "Travel"))
        );

        when(expenseRepository.findAll()).thenReturn(expenseEntities);

        List<ExpenseDto> allExpenses = expenseService.getAllExpenses();

        assertNotNull(allExpenses);
        assertEquals(expenseEntities.size(),allExpenses.size());

        for(int i=0;i<expenseEntities.size();i++){
            ExpenseDto dto = allExpenses.get(i);
            Expense entity = expenseEntities.get(i);
            assertEquals(entity.getIdLong(), dto.id());
            assertEquals(entity.getAmount(), dto.amount());
            assertEquals(entity.getExpenseDate(), dto.expenseDate());
            assertEquals(entity.getCategory().getId(), dto.categoryDto().id());
            assertEquals(entity.getCategory().getName(), dto.categoryDto().name());
        }
    }

    @Test
    void testUpdateExpense() {

        Long expenseId = 1L;
        Category category = new Category(2L,"travel");
        Expense oldExpense = new Expense(1L, BigDecimal.valueOf(1000.012), LocalDate.now(),category);
        Expense newExpense = new Expense(1L, BigDecimal.valueOf(3333.012), LocalDate.now(),category);
        ExpenseDto newexpenseDto = ExpenseMapper.mapToExpenseDto(newExpense);

        when(expenseRepository.findById(expenseId)).thenReturn(Optional.of(oldExpense));
        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));
        when(expenseRepository.save(any(Expense.class))).thenReturn(newExpense);

        ExpenseDto updatedExpense = expenseService.updateExpense(expenseId, newexpenseDto);

        assertNotNull(updatedExpense);
        assertEquals(newExpense.getIdLong(), updatedExpense.id());
        assertEquals(newExpense.getAmount(), updatedExpense.amount());
        assertEquals(newExpense.getExpenseDate(), updatedExpense.expenseDate());
        assertEquals(newExpense.getCategory().getId(), updatedExpense.categoryDto().id());
        assertEquals(newExpense.getCategory().getName(), updatedExpense.categoryDto().name());

    }

    @Test
    void testDeleteExpense() {

        Long expenseId = 1L;
        Expense expense = new Expense();
        expense.setIdLong(expenseId);

        when(expenseRepository.findById(expenseId)).thenReturn(Optional.of(expense));

        assertDoesNotThrow(() -> expenseService.deleteExpense(expenseId));

        verify(expenseRepository, times(1)).delete(expense);
    }
}