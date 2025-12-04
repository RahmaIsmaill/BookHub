package com.example.kitabhub.service;

import com.example.kitabhub.dto.UserRegisterDto;
import com.example.kitabhub.dto.UserResponseDto;

public interface UserService {
    UserResponseDto saveUser(UserRegisterDto user);
    void login(String email, String password);

}
