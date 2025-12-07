package com.example.kitabhub.controller;

import com.example.kitabhub.dto.UserLoginDto;
import com.example.kitabhub.dto.UserRegisterDto;
import com.example.kitabhub.dto.UserResponseDto;
import com.example.kitabhub.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("userApi/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<UserResponseDto> registerUser(@Valid  @RequestBody UserRegisterDto userRegisterDto) {
      UserResponseDto userResponseDto =userService.saveUser(userRegisterDto);
        return new ResponseEntity<>(userResponseDto,HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid  @RequestBody UserLoginDto userLoginDto) {
        userService.login(userLoginDto.getEmail(),userLoginDto.getPassword());
        return new  ResponseEntity<>("User Logged in Successfully ",HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<UserResponseDto> getUser(@RequestParam Long userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }
    @GetMapping("/user/email")
    public ResponseEntity<UserResponseDto> getUserbyemail(@RequestParam String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }
}
