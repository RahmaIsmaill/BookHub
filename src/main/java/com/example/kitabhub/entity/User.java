package com.example.kitabhub.entity;


import com.example.kitabhub.enums.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "users")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 10, message = "Password must be between 6 and 10 characters")
    private String password;

    @NotBlank(message = "Email is required")
    @Email(regexp = "^[A-Za-z0-9._%+-]+@([A-Za-z0-9.-]+)\\.(com|net|org|edu|ac\\.[a-z]{2,3})$",
            message = "Invalid email format.")
    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;
}
