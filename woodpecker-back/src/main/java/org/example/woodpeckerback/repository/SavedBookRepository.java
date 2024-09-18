package org.example.woodpeckerback.repository;

import org.example.woodpeckerback.entity.SavedBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavedBookRepository extends JpaRepository<SavedBook, Long> {
    boolean existsByUserIdAndBookId(Long userId, Long bookId);
}
