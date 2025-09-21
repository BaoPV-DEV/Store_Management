package com.example.backend.services;

import com.example.backend.dtos.UserDTO;
import com.example.backend.models.User;

public interface IUserService {

    User createUser(UserDTO userDTO);

    String login(String phoneNumber, String password);
}
