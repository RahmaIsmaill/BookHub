package com.example.kitabhub.repository;

import com.example.kitabhub.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepsitory extends JpaRepository<Like, Long> {
    boolean existsByUserIdAndBookId(Long userId, Long bookId);
    int deleteByUserIdAndBookId(Long userId, Long bookId);
}
