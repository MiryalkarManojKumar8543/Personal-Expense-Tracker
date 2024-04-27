package com.springboot.exptracker.service;

import java.util.List;

import com.springboot.exptracker.dto.CategoryDto;

public interface CategoryService {
	
	CategoryDto createCategory(CategoryDto categoryDto);
	
	CategoryDto getCategoryById(Long categoryId);
	
	List<CategoryDto> getAllCategories();
	
	CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto);
	
	void deleteCategory(Long categoryId);
}
