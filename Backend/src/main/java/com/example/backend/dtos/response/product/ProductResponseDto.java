package com.example.backend.dtos.response.product;

import com.example.backend.dtos.response.BaseResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@SuperBuilder
@JsonPropertyOrder({
        "id",
        "name",
        "price",
        "thumbnail",
        "description",
        "category_id",
        "createdAt",
        "updatedAt"
})
public class ProductResponseDto extends BaseResponse {
    private Long id;
    private String name;
    private BigDecimal price;
    private String thumbnail;
    private String description;

    @JsonProperty("category_id")
    private Long categoryId;
}
