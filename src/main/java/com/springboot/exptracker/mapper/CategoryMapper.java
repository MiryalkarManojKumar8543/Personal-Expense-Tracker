package com.springboot.exptracker.mapper;

import com.springboot.exptracker.dto.CategoryDto;
import com.springboot.exptracker.entity.Category;

public class CategoryMapper {
	
	//Map CategoryDto to Category entity
	public static Category mapToCategory(CategoryDto categoryDto) {
		return new Category(
				categoryDto.id(),
				categoryDto.name()
				);
	}
	
	//Map Category entity to CategoryDto
	public static CategoryDto mapToCategoryDto(Category category) {
		return new CategoryDto(
				category.getId(),
				category.getName()
				);
	}
}
