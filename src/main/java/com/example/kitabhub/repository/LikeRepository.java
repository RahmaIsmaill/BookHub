package com.example.kitabhub.repository;

import com.example.kitabhub.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {

    boolean existsByUserIdAndBookId(Long userId, Long bookId);

    void deleteByUserIdAndBookId(Long userId, Long bookId);

    int countByBookId(Long bookId);

    List<Like> findByUserId(Long userId);
}
