package com.example.backend.dtos.request.categories;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data

public class CategoryRequestDto {
    @NotEmpty(message = "Name is not empty.")
    private String name;
}
