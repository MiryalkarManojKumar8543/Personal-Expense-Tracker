package com.springboot.exptracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.exptracker.dto.CategoryDto;
import com.springboot.exptracker.entity.Category;
import com.springboot.exptracker.mapper.CategoryMapper;
import com.springboot.exptracker.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = CategoryController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateCategory() throws Exception{
        Category category = new Category(1L,"test category");
        CategoryDto categoryDto = CategoryMapper.mapToCategoryDto(category);

        given(categoryService.createCategory(ArgumentMatchers.any())).willAnswer((invocation -> invocation.getArgument(0)));

        ResultActions response = mockMvc.perform(post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryDto)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(categoryDto.id()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(categoryDto.name()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void getCategoryById() throws Exception {

        Long categoryId = 1L;
        Category category = new Category(categoryId,"new category");
        CategoryDto categoryDto = CategoryMapper.mapToCategoryDto(category);

        when(categoryService.getCategoryById(categoryId)).thenReturn(categoryDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/categories/{id}", categoryId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(categoryDto.id()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(categoryDto.name()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void testGetAllCategories() throws Exception{
        Category category1 = new Category(1L,"catgeory 1");
        Category category2 = new Category(2L,"catgeory 2");
        CategoryDto categoryDto1 = CategoryMapper.mapToCategoryDto(category1);
        CategoryDto categoryDto2 = CategoryMapper.mapToCategoryDto(category2);
        List<CategoryDto> categoryDtos = Arrays.asList(categoryDto1,categoryDto2);

        when(categoryService.getAllCategories()).thenReturn(categoryDtos);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(category1.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(category1.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(category2.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value(category2.getName()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void testUpdateCategory() throws Exception {

        Long categoryId = 1L;
        CategoryDto categoryDto = new CategoryDto(categoryId, "Updated Category");

        // Mocking behavior of CategoryService
        when(categoryService.updateCategory(anyLong(), any(CategoryDto.class))).thenReturn(categoryDto);

        // Perform the PUT request
        mockMvc.perform(MockMvcRequestBuilders.put("/api/categories/{id}", categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(categoryDto.id()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(categoryDto.name()));
    }

    @Test
    void testDeleteCategory() throws Exception {
        Long categoryId = 1L;
        Category category = new Category(categoryId,"delete category");
        CategoryDto categoryDto = CategoryMapper.mapToCategoryDto(category);

        // Mocking behavior of CategoryService
        doNothing().when(categoryService).deleteCategory(anyLong());

        // Perform the DELETE request
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/categories/{id}",  categoryId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}