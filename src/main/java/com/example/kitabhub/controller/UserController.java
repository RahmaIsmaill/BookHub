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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("userApi/v1")
@RequiredArgsConstructor
@Tag(name = "User APIs", description = "Operations related to user registration, login and fetching user data")
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Register a new user",
            description = "Creates a new user account using name, email, password, and role.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "User created successfully"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input data"
                    )
            }
    )
    @PostMapping("/registration")
    public ResponseEntity<UserResponseDto> registerUser(
            @Valid @RequestBody UserRegisterDto userRegisterDto) {

        UserResponseDto userResponseDto = userService.saveUser(userRegisterDto);
        return new ResponseEntity<>(userResponseDto, HttpStatus.CREATED);
    }

    @Operation(
            summary = "User login",
            description = "Login using email and password",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Login successful"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Invalid email or password"
                    )
            }
    )
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid @RequestBody UserLoginDto userLoginDto) {

        userService.login(userLoginDto.getEmail(), userLoginDto.getPassword());
        return new ResponseEntity<>("User Logged in Successfully", HttpStatus.OK);
    }

    @Operation(
            summary = "Get user by ID",
            description = "Fetches user data using user ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User retrieved successfully"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User not found"
                    )
            }
    )
    @GetMapping("/user")
    public ResponseEntity<UserResponseDto> getUser(
            @Parameter(description = "ID of the user", required = true)
            @RequestParam Long userId) {

        return ResponseEntity.ok(userService.getUser(userId));
    }

    @Operation(
            summary = "Get user by email",
            description = "Fetches user data using email",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User retrieved successfully"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User not found"
                    )
            }
    )
    @GetMapping("/user/email")
    public ResponseEntity<UserResponseDto> getUserByEmail(
            @Parameter(description = "Email of the user", required = true)
            @RequestParam String email) {

        return ResponseEntity.ok(userService.getUserByEmail(email));
    }
}
