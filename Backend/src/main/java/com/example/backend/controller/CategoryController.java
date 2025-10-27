package com.example.backend.controller;

import com.example.backend.dtos.request.categories.CategoryRequestDto;
import com.example.backend.models.Category;
import com.example.backend.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/categories")
// Dependency injection
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    /**
     * Create new category
     * @param categoryRequestDTO request
     * @param result error result
     * @return new category information
     */
    @PostMapping("")
    public ResponseEntity<?> createCategories(@RequestBody @Valid CategoryRequestDto categoryRequestDTO, BindingResult result) {
        if (result.hasErrors()) {
            List<String> resultErrorList = result.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(resultErrorList);
        }

        categoryService.createCategory(categoryRequestDTO);
        return ResponseEntity.ok("Successfully created categories!" + categoryRequestDTO.getName());
    }

    /**
     * Get all category
     * @return category list
     */
    @GetMapping("")
    public ResponseEntity<?> getAllCategories() {
        List<Category> categoryList = categoryService.getAllCategories();
        return ResponseEntity.ok(categoryList);
    }

    /**
     * Update exist category
     * @param id id of category need update
     * @param categoryRequestDTO request
     * @param result error result
     * @return update category information
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategories(@PathVariable Long id,
                                              @RequestBody @Valid CategoryRequestDto categoryRequestDTO, BindingResult result) {
        if (result.hasErrors()) {
            List<String> resultErrorList = result.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(resultErrorList);
        }
        Category updateCategory = categoryService.updateCategory(id, categoryRequestDTO);
        return ResponseEntity.ok("Update successfully with id = " + id + "Data: " + updateCategory);
    }

    /**
     * Delete exist category
     * @param id id of category need delete
     * @return success
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategories(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Delete successfully with id = " + id);
    }
}
