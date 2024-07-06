package com.springboot.exptracker.service;

import com.springboot.exptracker.dto.CategoryDto;
import com.springboot.exptracker.entity.Category;
import com.springboot.exptracker.mapper.CategoryMapper;
import com.springboot.exptracker.repository.CategoryRepository;
import com.springboot.exptracker.service.impl.CategoryServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    void testCreateCategory() {

        Category newCategory = new Category(1L,"studying"); //Mock object

        CategoryDto categoryDto = CategoryMapper.mapToCategoryDto(newCategory);

        when(categoryRepository.save(any(Category.class))).thenReturn(newCategory);

        CategoryDto savedCategory = categoryService.createCategory(categoryDto);

        Assertions.assertThat(savedCategory).isNotNull();

    }

    @Test
    void getCategoryById() {
        Long categoryId = 3L;
        Category mockCategory = new Category(categoryId, "Test Category");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(mockCategory));

        CategoryDto result = categoryService.getCategoryById(categoryId);

        assertEquals(categoryId, result.id());
        assertEquals("Test Category", result.name());

    }

    @Test
    void testGetAllCategories() {

        Category category1 = new Category(1L,"catgeory 1");
        Category category2 = new Category(2L,"catgeory 2");

        List<Category> categories = Arrays.asList(category1,category2);

        when(categoryRepository.findAll()).thenReturn(categories);

        List<CategoryDto> categoryDtos = categoryService.getAllCategories();

        Assertions.assertThat(categoryDtos).isNotNull();
        assertThat(2).isEqualTo(categoryDtos.size());

        CategoryDto categoryDto1 = categoryDtos.get(0);
        assertEquals(category1.getId(), categoryDto1.id());
        assertEquals(category1.getName(), categoryDto1.name());

        CategoryDto categoryDto2 = categoryDtos.get(1);
        assertEquals(category2.getId(), categoryDto2.id());
        assertEquals(category2.getName(), categoryDto2.name());

    }

    @Test
    void updateCategory() {
        Long categoryId = 1L;
        Category oldCategory = new Category(categoryId,"old category");

        Category newCategory = new Category();
        newCategory.setName("new category");

        CategoryDto newCategoryDto = CategoryMapper.mapToCategoryDto(newCategory);

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(oldCategory));
        when(categoryRepository.save(any(Category.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CategoryDto result = categoryService.updateCategory(categoryId,newCategoryDto);

        assertEquals(newCategoryDto.name(),result.name());
        verify(categoryRepository,times(1)).findById(categoryId);
        verify(categoryRepository,times(1)).save(any(Category.class));
    }

    @Test
    void deleteCategory() {
        Long categoryId = 1L;
        Category category = new Category(categoryId, "TestCategory");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        assertDoesNotThrow(() -> categoryService.deleteCategory(categoryId));

        verify(categoryRepository, times(1)).delete(category);
    }
}