package org.example.woodpeckerback.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.woodpeckerback.entity.Book;
import org.example.woodpeckerback.entity.Note;
import org.example.woodpeckerback.entity.User;
import org.example.woodpeckerback.exception.CustomException;
import org.example.woodpeckerback.exception.ErrorCode;
import org.example.woodpeckerback.repository.BookRepository;
import org.example.woodpeckerback.repository.NoteRepository;
import org.example.woodpeckerback.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Transactional
    public void saveNote(Long userId, String isbn, String content) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Book book = bookRepository.findByIsbn(isbn).orElseThrow(
                () -> new CustomException(ErrorCode.BOOK_NOT_FOUND));

        Note newNote = Note.builder()
                .book(book)
                .user(user)
                .content(content)
                .build();
        noteRepository.save(newNote);
    }

}
