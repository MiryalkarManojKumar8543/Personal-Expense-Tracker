package com.springboot.exptracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.exptracker.dto.CategoryDto;
import com.springboot.exptracker.dto.ExpenseDto;
import com.springboot.exptracker.entity.Category;
import com.springboot.exptracker.entity.Expense;
import com.springboot.exptracker.mapper.ExpenseMapper;
import com.springboot.exptracker.service.ExpenseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ExpenseController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class ExpenseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExpenseService expenseService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateExpense() throws Exception {

        Category category = new Category(1L,"food");
        Expense expense = new Expense(1L, BigDecimal.valueOf(789.012), LocalDate.now(),category);
        ExpenseDto expenseDto = ExpenseMapper.mapToExpenseDto(expense);

        when(expenseService.createExpense(any(ExpenseDto.class))).thenReturn(expenseDto);

        mockMvc.perform(post("/api/expenses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(expenseDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.amount").value(789.012))
                .andExpect(jsonPath("$.categoryDto.id").value(1L))
                .andExpect(jsonPath("$.categoryDto.name").value("food"));

    }

    @Test
    void testGetExpenseById() throws Exception {

        Long expenseId = 1L;
        Category category = new Category(2L,"travel");
        Expense expense = new Expense(expenseId, BigDecimal.valueOf(1000.012), LocalDate.now(),category);
        ExpenseDto expenseDto = ExpenseMapper.mapToExpenseDto(expense);

        when(expenseService.getExpenseById(expenseId)).thenReturn(expenseDto);

        mockMvc.perform(get("/api/expenses/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.amount").value(1000.012))
                .andExpect(jsonPath("$.categoryDto.id").value(2L))
                .andExpect(jsonPath("$.categoryDto.name").value("travel"));
    }

    @Test
    void testGetAllExpenses() throws Exception {
        Expense expense1 = new Expense(1L, new BigDecimal("200.00"), LocalDate.now(), new Category(3L, "Entertainment"));
        Expense expense2 = new Expense(2L, new BigDecimal("75.50"), LocalDate.now(), new Category(4L, "Utilities"));
        ExpenseDto expenseDto1 = ExpenseMapper.mapToExpenseDto(expense1);
        ExpenseDto expenseDto2 = ExpenseMapper.mapToExpenseDto(expense2);

        List<ExpenseDto> expenseEntities = Arrays.asList(expenseDto1,expenseDto2);

        when(expenseService.getAllExpenses()).thenReturn(expenseEntities);

        mockMvc.perform(get("/api/expenses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].amount").value(200.00))
                .andExpect(jsonPath("$[0].categoryDto.id").value(3L))
                .andExpect(jsonPath("$[0].categoryDto.name").value("Entertainment"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].amount").value(75.50))
                .andExpect(jsonPath("$[1].categoryDto.id").value(4L))
                .andExpect(jsonPath("$[1].categoryDto.name").value("Utilities"));
    }

    @Test
    void testUpdateExpense() throws Exception{

        ExpenseDto expenseDto = new ExpenseDto(1L, new BigDecimal("300.00"), LocalDate.now(), new CategoryDto(5L, "Shopping"));

        when(expenseService.updateExpense(1L, expenseDto)).thenReturn(expenseDto);

        mockMvc.perform(put("/api/expenses/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(expenseDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(300.00))
                .andExpect(jsonPath("$.categoryDto.id").value(5L))
                .andExpect(jsonPath("$.categoryDto.name").value("Shopping"));
    }

    @Test
    void testDeleteExpense() throws Exception{

        Long expenseId = 1L;
        Expense expense1 = new Expense(expenseId, new BigDecimal("200.00"), LocalDate.now(), new Category(3L, "Entertainment"));

        doNothing().when(expenseService).deleteExpense(1L);

        mockMvc.perform(delete("/api/expenses/{id}", expenseId))
                .andExpect(status().isOk())
                .andExpect(content().string("Expense successfully deleted"));
    }
}