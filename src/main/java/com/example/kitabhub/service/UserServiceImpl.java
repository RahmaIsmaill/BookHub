package com.example.kitabhub.service;


import com.example.kitabhub.dto.UserRegisterDto;
import com.example.kitabhub.dto.UserResponseDto;
import com.example.kitabhub.entity.User;
import com.example.kitabhub.enums.Role;
import com.example.kitabhub.exception.CustomValidationException;
import com.example.kitabhub.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponseDto saveUser(UserRegisterDto user) {

        Map<String, String> errors = new HashMap<>();

        if (userRepository.existsByEmail(user.getEmail())) {
            errors.put("email", "Email already exists");
        }

        Role role = null;
        try {
            role = Role.valueOf(user.getRole().toUpperCase());
        } catch (IllegalArgumentException ex) {
            errors.put("role", "Invalid role. Allowed: ADMIN, USER");
        }


        if (!errors.isEmpty()) {
            throw new CustomValidationException(errors);
        }

        User userEntity = User.builder()
                .username(user.getFirstName() + " " + user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(role)
                .build();

        userRepository.save(userEntity);

        return UserResponseDto.builder()
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .role(userEntity.getRole())
                .build();
    }

    @Override
    public void login(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new CustomValidationException(Map.of("Error", "User Not Found")));
            if (!user.getPassword().equals(password)) {
               throw  new CustomValidationException(Map.of("Error", "Wrong Password"));
            }


    }

    @Override
    public UserResponseDto getUser(Long userId) {
        User userEntity = userRepository.findById(userId).orElseThrow(() -> new CustomValidationException(Map.of("Error", "User not found")));
        return UserResponseDto.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .role(userEntity.getRole())
                .build();
    }

    @Override
    public UserResponseDto getUserByEmail(String email) {
        User userEntity = userRepository.findByEmail(email).orElseThrow(() -> new CustomValidationException(Map.of("Error", "User not found")));
        return UserResponseDto.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .role(userEntity.getRole())
                .build();
    }


}
