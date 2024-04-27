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

import com.springboot.exptracker.dto.CategoryDto;
import com.springboot.exptracker.service.CategoryService;

@RestController
@RequestMapping("/api/categories")  //Define the base URI
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	//Build create category REST API
	@PostMapping
	public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto){
	   CategoryDto category = categoryService.createCategory(categoryDto);
	   return new ResponseEntity<CategoryDto>(category, HttpStatus.CREATED);
	}
	
	//Build get category by Id REST API
	@GetMapping("{id}")
	public ResponseEntity<CategoryDto> getCategoryById(@PathVariable("id") Long categoryId){
		CategoryDto category = categoryService.getCategoryById(categoryId);
		return ResponseEntity.ok(category);
	}
	
	//Build get all categories REST API
	@GetMapping
	public ResponseEntity<List<CategoryDto>> getAllCategories(){
		List<CategoryDto> categories = categoryService.getAllCategories();
		return ResponseEntity.ok(categories);
	}
	
	//Build update category REST API
	@PutMapping("{id}")
	public ResponseEntity<CategoryDto> updateCategory(@PathVariable("id") Long CategoryId, @RequestBody CategoryDto categoryDto){
		CategoryDto updateCategory = categoryService.updateCategory(CategoryId, categoryDto);
		return ResponseEntity.ok(updateCategory);
	}
	
	//Build delete category REST API
	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteCategory(@PathVariable("id") Long categoryId){
		categoryService.deleteCategory(categoryId);
		return ResponseEntity.ok("Category deleted successfully");
	}
}
