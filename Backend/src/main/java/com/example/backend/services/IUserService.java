package com.example.backend.services;

import com.example.backend.dtos.request.users.UserRegisterRequestDto;
import com.example.backend.models.User;

public interface IUserService {

    User createUser(UserRegisterRequestDto userRegisterRequestDto);

    String login(String phoneNumber, String password);
}
