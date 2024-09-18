package org.example.woodpeckerback.service;

import lombok.RequiredArgsConstructor;
import org.example.woodpeckerback.dto.NaverBookApiResponse;
import org.example.woodpeckerback.dto.NaverBookItem;
import org.example.woodpeckerback.entity.Book;
import org.example.woodpeckerback.entity.SavedBook;
import org.example.woodpeckerback.entity.User;
import org.example.woodpeckerback.repository.BookRepository;
import org.example.woodpeckerback.repository.SavedBookRepository;
import org.example.woodpeckerback.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;


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
    private final SavedBookRepository savedBookRepository;
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
                () -> new RuntimeException("User not Found"));
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

        boolean alreadySaved = savedBookRepository.existsByUserIdAndBookId(userId, book.getId());
        if (alreadySaved) {
            return false;
        } else {
            SavedBook savedBook = SavedBook.builder()
                    .user(user)
                    .book(book)
                    .build();
            savedBookRepository.save(savedBook);
            return true;
        }
    }

}
