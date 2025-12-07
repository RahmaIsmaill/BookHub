package com.example.kitabhub.service;

import com.example.kitabhub.dto.UserRegisterDto;
import com.example.kitabhub.dto.UserResponseDto;
import com.example.kitabhub.entity.User;

import java.util.Optional;

public interface UserService {
    UserResponseDto saveUser(UserRegisterDto user);
    void login(String email, String password);
    UserResponseDto getUser(Long userId);
    UserResponseDto getUserByEmail(String email);
}
