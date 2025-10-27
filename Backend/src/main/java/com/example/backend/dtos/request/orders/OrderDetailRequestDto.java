package com.example.backend.dtos.request.orders;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data

public class OrderDetailRequestDto {
    @JsonProperty("order_id")
    @Min(value = 1, message = "Order's ID must be greater than 0.")
    private Long orderId;

    @JsonProperty("product_id")
    @Min(value = 1, message = "Product's ID must be greater than 0.")
    private String productId;

    private String email;

    @Min(value = 0, message = "User's ID must be greater than or equal 0.")
    private Float price;

    @JsonProperty("number_of_products")
    @Min(value = 1, message = "Product's quantity must be greater than 0.")
    private int numberOfProducts;

    @JsonProperty("total_money")
    @Min(value = 0, message = "User's ID must be greater than or equal 0.")
    private int totalMoney;

    private String color;
}
