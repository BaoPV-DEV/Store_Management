package com.example.backend.controller;

import com.example.backend.dtos.UserDTO;
import com.example.backend.dtos.UserLoginDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/user")
public class UserController extends BaseController{
    @PostMapping("/register")
    public ResponseEntity<?> createUser(
            @Valid @RequestBody UserDTO userDTO,
            BindingResult result) {
        return getResponseEntity(result, "Create user successfully! " + userDTO, userDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid @RequestBody UserLoginDTO userLoginDTO,
            BindingResult result) {
        return getResponseEntity(result, "Login successfully! " + userLoginDTO, userLoginDTO);
    }
}
