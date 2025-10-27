package com.example.backend.controller;

import com.example.backend.dtos.request.users.UserRegisterRequestDto;
import com.example.backend.dtos.request.users.UserLoginRequestDto;
import com.example.backend.models.User;
import com.example.backend.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/user")
public class UserController extends BaseController{
    private final UserService userService;

    // Dependency injection
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(
            @Valid @RequestBody UserRegisterRequestDto userRegisterRequestDto,
            BindingResult result) {
        if (result.hasErrors()) {
            List<String> resultErrorList = result.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(resultErrorList);
        }
        User newUser = userService.createUser(userRegisterRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid @RequestBody UserLoginRequestDto userLoginRequestDto,
            BindingResult result) {
        if (result.hasErrors()) {
            List<String> resultErrorList = result.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(resultErrorList);
        }
        String token = userService.login(userLoginRequestDto.getPhoneNumber(), userLoginRequestDto.getPassword());
        return ResponseEntity.ok("Login successfully!" + token);
    }
}
