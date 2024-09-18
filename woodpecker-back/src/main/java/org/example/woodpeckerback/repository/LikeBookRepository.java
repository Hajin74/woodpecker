package org.example.woodpeckerback.repository;

import org.example.woodpeckerback.entity.LikeBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeBookRepository extends JpaRepository<LikeBook, Long> {
    Optional<LikeBook> findByUserIdAndBookId(Long userId, Long bookId);
}
