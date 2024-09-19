package org.example.woodpeckerback.repository;

import org.example.woodpeckerback.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {
}
