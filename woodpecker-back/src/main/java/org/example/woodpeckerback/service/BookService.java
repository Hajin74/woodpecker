package org.example.woodpeckerback.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.woodpeckerback.dto.NaverBookApiResponse;
import org.example.woodpeckerback.dto.NaverBookItem;
import org.example.woodpeckerback.entity.Book;
import org.example.woodpeckerback.entity.LikeBook;
import org.example.woodpeckerback.entity.SaveBook;
import org.example.woodpeckerback.entity.User;
import org.example.woodpeckerback.exception.CustomException;
import org.example.woodpeckerback.exception.ErrorCode;
import org.example.woodpeckerback.repository.BookRepository;
import org.example.woodpeckerback.repository.LikeBookRepository;
import org.example.woodpeckerback.repository.SaveBookRepository;
import org.example.woodpeckerback.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

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
    public boolean saveBook(Long userId, NaverBookItem naverBookItem) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Optional<Book> targetBook = bookRepository.findByIsbn(naverBookItem.isbn());

        Book book;
        if (targetBook.isEmpty()) {
            Book newBook = Book.builder()
                    .title(naverBookItem.title())
                    .author(naverBookItem.author())
                    .description(naverBookItem.description())
                    .publisher(naverBookItem.publisher())
                    .isbn(naverBookItem.isbn())
                    .build();
            bookRepository.save(newBook);
            book = newBook;
        } else {
            book = targetBook.get();
        }

        boolean alreadySaved = saveBookRepository.existsByUserIdAndBookId(userId, book.getId());
        if (alreadySaved) {
            return false;
        } else {
            SaveBook saveBook = SaveBook.builder()
                    .user(user)
                    .book(book)
                    .build();
            saveBookRepository.save(saveBook);
            return true;
        }
    }

    @Transactional
    public boolean likeBook(Long userId, String isbn) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Optional<Book> targetBook = bookRepository.findByIsbn(isbn);
        if (targetBook.isEmpty()) {
            return false; // 책이 존재하지 않음
        }
        Book book = targetBook.get();

        Optional<LikeBook> likeBook = likeBookRepository.findByUserIdAndBookId(userId, book.getId());
        if (likeBook.isPresent()) {
            log.info("already liked");
            return false;
        } else {
            LikeBook newLike = LikeBook.builder()
                    .user(user)
                    .book(book)
                    .build();
            likeBookRepository.save(newLike);
            return true;
        }
    }

}
