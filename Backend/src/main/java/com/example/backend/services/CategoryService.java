package com.example.backend.services;

import com.example.backend.dtos.request.categories.CategoryRequestDto;
import com.example.backend.exceptions.DataNotFoundException;
import com.example.backend.models.Category;
import com.example.backend.repositories.ICategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService{
    private final ICategoryRepository categoryRepository;

    @Override
    public Category createCategory(CategoryRequestDto categoryRequestDTO) {
        Category newCategory = Category.builder().name(categoryRequestDTO.getName()).build();
        return categoryRepository.save(newCategory);
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Category not found with id: " + id));
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category updateCategory(Long categoryId, CategoryRequestDto categoryRequestDTO) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if (optionalCategory.isEmpty()){
            throw new DataNotFoundException("Category not found with id: " + categoryId);
        }

        Category category = optionalCategory.get();
        category.setName(categoryRequestDTO.getName());
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new EntityNotFoundException("Category not found with id: " + categoryId);
        }
        categoryRepository.deleteById(categoryId);
    }
}
