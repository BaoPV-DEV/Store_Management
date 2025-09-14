package com.example.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

public class BaseController {

    public static <T> ResponseEntity<?> getResponseEntity(BindingResult result, String successMessage, T dto) {
        try {
            if (result.hasErrors()) {
                List<String> resultErrorList = result.getAllErrors()
                        .stream()
                        .map(ObjectError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(resultErrorList);
            }
            return ResponseEntity.ok(successMessage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
