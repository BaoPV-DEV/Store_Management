package com.example.backend.dtos.request.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data

public class UserLoginRequestDto {
    @NotBlank(message = "Phone number is required")
    @Size(min = 10, max = 10, message = "Phone number must be exactly 10 digits")
    @JsonProperty("phone_number")
    private String phoneNumber;

    @NotBlank(message = "Password is required")
    private String password;
}
