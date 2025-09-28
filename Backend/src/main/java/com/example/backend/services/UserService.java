package com.example.backend.services;

import com.example.backend.dtos.request.users.UserRegisterRequestDto;
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
    public User createUser(UserRegisterRequestDto userRegisterRequestDto) {
        boolean phoneExists = userRepository.existsByPhoneNumber(userRegisterRequestDto.getPhoneNumber());
        if (phoneExists) {
            throw new AlreadyExistsException("Phone number already exists: " + userRegisterRequestDto.getPhoneNumber());
        }

        User user = User.builder()
                .fullName(userRegisterRequestDto.getFullName())
                .phoneNumber(userRegisterRequestDto.getPhoneNumber())
                .dateOfBirth(userRegisterRequestDto.getDateOfBirth().toString())
                .address(userRegisterRequestDto.getAddress())
                .googleAccountId(userRegisterRequestDto.getGoogleAccountId())
                .facebookAccountId(userRegisterRequestDto.getFacebookAccountId())
                .build();

        Optional<Role> role = roleRepository.findById(userRegisterRequestDto.getRoleId());
        if (role.isEmpty()) {
            throw new DataNotFoundException("Role is not existed!");
        }
        user.setRole(role.get());

        // check longin by OAuth2
        if(userRegisterRequestDto.getFacebookAccountId() == 0 && userRegisterRequestDto.getGoogleAccountId() == 0){
            String password = userRegisterRequestDto.getPassword();
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
