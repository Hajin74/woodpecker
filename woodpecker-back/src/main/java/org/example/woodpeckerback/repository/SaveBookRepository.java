package org.example.woodpeckerback.repository;

import org.example.woodpeckerback.entity.SaveBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaveBookRepository extends JpaRepository<SaveBook, Long> {
    boolean existsByUserIdAndBookId(Long userId, Long bookId);
}
