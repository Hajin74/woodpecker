package org.example.woodpeckerback.repository;

import org.example.woodpeckerback.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
