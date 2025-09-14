package com.example.backend.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data

public class UserDTO {
    @Size(max = 100)
    @JsonProperty("fullname")
    private String fullName;

    @NotBlank(message = "Phone number is required")
    @Size(min = 10, max = 10, message = "Phone number must be exactly 10 digits")
    @JsonProperty("phone_number")
    private String phoneNumber;

    @Size(max = 200)
    private String address;

    @NotBlank(message = "Password is required")
    @Size(max = 100)
    private String password = "";

    @Size(max = 100)
    @JsonProperty("retype_password")
    private String reTypePassword = "";

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("date_of_birth")
    private Date dateOfBirth;

    @JsonProperty("facebook_account_id")
    private Integer facebookAccountId;

    @JsonProperty("google_account_id")
    private Integer googleAccountId;

    @JsonProperty("role_id")
    @NotNull(message = "Role is required")
    private Long roleId;

    @AssertTrue(message = "Passwords do not match")
    public boolean isPasswordsMatching() {
        if (password == null || reTypePassword == null) return false;
        return password.equals(reTypePassword);
    }
}
