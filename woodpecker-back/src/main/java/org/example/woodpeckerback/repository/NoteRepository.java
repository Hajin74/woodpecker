package org.example.woodpeckerback.repository;

import org.example.woodpeckerback.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findAllByUserIdAndBookIsbn(Long userId, String isbn);

    Optional<Note> findByIdAndIsDeletedFalse(Long noteId);
}
