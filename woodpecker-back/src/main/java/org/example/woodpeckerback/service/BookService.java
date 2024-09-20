package org.example.woodpeckerback.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.woodpeckerback.dto.*;
import org.example.woodpeckerback.entity.*;
import org.example.woodpeckerback.exception.CustomException;
import org.example.woodpeckerback.exception.ErrorCode;
import org.example.woodpeckerback.repository.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {

    @Value("${NAVER_API_HOST}")
    private String bookAPIHost;

    @Value("${NAVER_BOOK_API_URL}")
    private String bookAPIUrl;

    @Value("${NAVER_CLIENT_ID}")
    private String clientId;

    @Value("${NAVER_CLIENT_SECRET}")
    private String clientSecret;

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final NoteRepository noteRepository;
    private final SaveBookRepository saveBookRepository;
    private final LikeBookRepository likeBookRepository;
    private final WebClient.Builder webClientBuilder;

    @Transactional(readOnly = true)
    public NaverBookApiResponse searchBook(String query, Integer display, Integer start, String sort) {
        WebClient webClient = webClientBuilder.build();

        Mono<NaverBookApiResponse> responseMono = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host(bookAPIHost)
                        .path(bookAPIUrl)
                        .queryParam("query", query)
                        .queryParam("display", display != null ? display : 10)
                        .queryParam("start", start != null ? start : 1)
                        .queryParam("sort", sort != null ? sort : "sim")
                        .build())
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .header("X-Naver-Client-Id", clientId)
                .header("X-Naver-Client-Secret", clientSecret)
                .retrieve()
                .bodyToMono(NaverBookApiResponse.class);

        return responseMono.block();
    }

    @Transactional
    public void saveBook(Long userId, SaveBookInput input) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Optional<Book> targetBook = bookRepository.findByIsbn(input.getIsbn());

        Book book;
        if (targetBook.isEmpty()) {
            Book newBook = Book.builder()
                    .title(input.getTitle())
                    .author(input.getAuthor())
                    .description(input.getDescription())
                    .publisher(input.getPublisher())
                    .isbn(input.getIsbn())
                    .build();
            bookRepository.save(newBook);
            book = newBook;
        } else {
            book = targetBook.get();
        }

        boolean alreadySaved = saveBookRepository.existsByUserIdAndBookId(userId, book.getId());
        if (alreadySaved) {
            throw new CustomException(ErrorCode.BOOK_ALREADY_SAVED);
        } else {
            SaveBook saveBook = SaveBook.builder()
                    .user(user)
                    .book(book)
                    .build();
            saveBookRepository.save(saveBook);
        }
    }

    @Transactional
    public void likeBook(Long userId, String isbn) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Optional<Book> targetBook = bookRepository.findByIsbn(isbn);
        if (targetBook.isEmpty()) {
            throw new CustomException(ErrorCode.BOOK_NOT_FOUND);
        }
        Book book = targetBook.get();

        Optional<LikeBook> likeBook = likeBookRepository.findByUserIdAndBookId(userId, book.getId());
        if (likeBook.isPresent()) {
            throw new CustomException(ErrorCode.BOOK_ALREADY_LIKED);
        } else {
            LikeBook newLike = LikeBook.builder()
                    .user(user)
                    .book(book)
                    .build();
            likeBookRepository.save(newLike);
        }
    }

    @Transactional(readOnly = true)
    public ShareBookAndNoteResponse shareBookAndNotes(Long userId, Long bookId) {
        Optional<Book> targetBook = bookRepository.findById(bookId);
        if (targetBook.isEmpty()) {
            throw new CustomException(ErrorCode.BOOK_NOT_FOUND);
            // todo: rest 전용 예외 만들긴
        }
        Book book = targetBook.get();
        BookItemResponse bookItemResponse = new BookItemResponse(book.getId(), book.getTitle(), book.getAuthor(), book.getPublisher(), book.getDescription(), book.getIsbn());

        List<Note> notes = noteRepository.findAllByUserIdAndBookIsbn(userId, book.getIsbn());
        Collections.reverse(notes);

        List<NoteDetailResponse> noteDetailResponses = notes.stream()
                .map((note) -> new NoteDetailResponse(note.getId(), note.getUser().getId(), note.getBook().getId(), note.getContent()))
                .toList();

        return new ShareBookAndNoteResponse(bookItemResponse, noteDetailResponses);
    }

}
