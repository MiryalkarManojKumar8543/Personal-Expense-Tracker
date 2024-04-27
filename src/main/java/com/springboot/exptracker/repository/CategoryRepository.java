package com.springboot.exptracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.exptracker.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

}
