package com.example.backend.services;

import com.example.backend.dtos.CategoryDTO;
import com.example.backend.models.Category;

import java.util.List;

public interface ICategoryService {

    Category createCategory(CategoryDTO categoryDTO);

    Category getCategoryById(Long id);

    List<Category> getAllCategories();

    Category updateCategory(Long categoryId, CategoryDTO category);

    void deleteCategory(Long categoryId);
}
