package com.example.backend.services;

import com.example.backend.dtos.UserDTO;
import com.example.backend.exceptions.AlreadyExistsException;
import com.example.backend.exceptions.DataNotFoundException;
import com.example.backend.models.Role;
import com.example.backend.models.User;
import com.example.backend.repositories.IRoleRepository;
import com.example.backend.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;

    @Override
    public User createUser(UserDTO userDTO) {
        boolean phoneExists = userRepository.existsByPhoneNumber(userDTO.getPhoneNumber());
        if (phoneExists) {
            throw new AlreadyExistsException("Phone number already exists: " + userDTO.getPhoneNumber());
        }

        User user = User.builder()
                .fullName(userDTO.getFullName())
                .phoneNumber(userDTO.getPhoneNumber())
                .dateOfBirth(userDTO.getDateOfBirth().toString())
                .address(userDTO.getAddress())
                .googleAccountId(userDTO.getGoogleAccountId())
                .facebookAccountId(userDTO.getFacebookAccountId())
                .build();

        Optional<Role> role = roleRepository.findById(userDTO.getRoleId());
        if (role.isEmpty()) {
            throw new DataNotFoundException("Role is not existed!");
        }
        user.setRole(role.get());

        // check longin by OAuth2
        if(userDTO.getFacebookAccountId() == 0 && userDTO.getGoogleAccountId() == 0){
            String password = userDTO.getPassword();
//            String encodePassword = passwordEncode.encode(userDTO.getPassword());
//            user.setPassword(encodePassword);
        }
        return userRepository.save(user);
    }

    @Override
    public String login(String phoneNumber, String password) {
        return "";
    }
}
