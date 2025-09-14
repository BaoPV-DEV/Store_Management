package com.example.backend.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data

public class CategoryDTO {
    @NotEmpty(message = "Name is not empty.")
    private String nameCategory;
}
