package com.example.backend.services;

import com.example.backend.dtos.request.categories.CategoryRequestDto;
import com.example.backend.models.Category;

import java.util.List;

public interface ICategoryService {

    Category createCategory(CategoryRequestDto categoryRequestDTO);

    Category getCategoryById(Long id);

    List<Category> getAllCategories();

    Category updateCategory(Long categoryId, CategoryRequestDto category);

    void deleteCategory(Long categoryId);
}
