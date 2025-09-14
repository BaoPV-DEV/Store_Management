package com.example.backend.controller;

import com.example.backend.dtos.CategoryDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    @GetMapping("")
    public ResponseEntity<String> getAllCategories(
            @RequestParam int page,
            @RequestParam int limit) {
        return ResponseEntity.ok(String.format("Get category with page = %d, limit = %d", page, limit));
    }

    @PostMapping("")
    public ResponseEntity<?> createCategories(@RequestBody @Valid CategoryDTO categoryDTO, BindingResult result) {
        if (result.hasErrors()) {
            List<String> resultErrorList = result.getAllErrors()
                                        .stream()
                                        .map(ObjectError::getDefaultMessage)
                                        .toList();
            return ResponseEntity.badRequest().body(resultErrorList);
        }
        return ResponseEntity.ok("Successfully created categories!" + categoryDTO.getNameCategory());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategories(@PathVariable Long id) {
        return ResponseEntity.ok("Update successfully with id = " + id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategories(@PathVariable Long id) {
        return ResponseEntity.ok("Delete successfully with id = " + id);
    }
}
