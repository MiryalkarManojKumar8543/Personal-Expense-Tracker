package com.springboot.exptracker.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.exptracker.dto.CategoryDto;
import com.springboot.exptracker.entity.Category;
import com.springboot.exptracker.mapper.CategoryMapper;
import com.springboot.exptracker.repository.CategoryRepository;
import com.springboot.exptracker.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		
		//convert CategoryDto to category entity
		Category category = CategoryMapper.mapToCategory(categoryDto);
		
		//save category object into database table - categories
		Category savedCategory = categoryRepository.save(category);
		
		//convert saved category into categoryDto
		return CategoryMapper.mapToCategoryDto(savedCategory);
	}

	@Override
	public CategoryDto getCategoryById(Long categoryId) {
		Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new RuntimeException("Category not found with id: "+categoryId));
		return CategoryMapper.mapToCategoryDto(category);
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		List<Category> categories = categoryRepository.findAll();
		return categories.stream().map((category)->CategoryMapper.mapToCategoryDto(category)).collect(Collectors.toList());
	}

	@Override
	public CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto) {
		// get category entity from the database by category id
		Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new RuntimeException("Category not found with id: "+categoryId));
		
		//update the category entity object and save to database table
		category.setName(categoryDto.name());
		Category updatedCategory = categoryRepository.save(category);
		return CategoryMapper.mapToCategoryDto(updatedCategory);
	}

	@Override
	public void deleteCategory(Long categoryId) {
		
		//check if a category with given id exists in a database
		Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new RuntimeException("Category not found with id: "+categoryId));
		categoryRepository.delete(category);
	}
	
}
