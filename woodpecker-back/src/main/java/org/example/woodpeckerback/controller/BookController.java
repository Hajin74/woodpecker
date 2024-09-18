package org.example.woodpeckerback.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.woodpeckerback.dto.NaverBookApiResponse;
import org.example.woodpeckerback.service.BookService;
import org.example.woodpeckerback.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final JwtService jwtService;

    @GetMapping
    public ResponseEntity<NaverBookApiResponse> searchBook(@RequestHeader("Cookie") String cookie, @RequestParam("query") String query, @RequestParam("display") int display, @RequestParam("start") int start, @RequestParam("sort") String sort) {
        String authorization = extractAuthorizationToken(cookie);
        Long kakaoId = jwtService.getKakaoId(authorization);
        log.info("searchBook - api : {}", kakaoId);
        NaverBookApiResponse response = bookService.searchBook(query, display, start, sort);

        return ResponseEntity.ok(response);
    }

    private String extractAuthorizationToken(String cookie) {
        if (cookie != null && cookie.contains("Authorization=")) {
            return cookie.split("Authorization=")[1].split(";")[0];
        }
        return null;
    }

}
