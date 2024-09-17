package org.example.woodpeckerback.service;

import lombok.RequiredArgsConstructor;
import org.example.woodpeckerback.dto.NaverBookApiResponse;
import org.example.woodpeckerback.repository.BookRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


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

    private final BookRepository bookRepository;
    private final WebClient.Builder webClientBuilder;

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

}
