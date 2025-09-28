package com.example.backend.dtos.request.products;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProductImageRequestDto {
    @JsonProperty("image_url")
    @Size(min = 5, max = 300, message = "ImageUrl must be less than or equal to 300 characters.")
    private String imageUrl;

    @JsonProperty("product_id")
    @Min(value = 1, message = "ProductId must be greater than or equal to 1.")
    private Long productId;
}
