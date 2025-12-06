package com.example.kitabhub.dto;

import com.example.kitabhub.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto
{


    private String username;
    private String email;
    private Role role = Role.USER;
}
