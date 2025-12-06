package com.example.kitabhub.repository;

import com.example.kitabhub.dto.BookResponseDto;
import com.example.kitabhub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
}
