package org.example.woodpeckerback.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.woodpeckerback.dto.NaverBookApiResponse;
import org.example.woodpeckerback.entity.User;
import org.example.woodpeckerback.service.BookService;
import org.example.woodpeckerback.service.JwtService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final JwtService jwtService;

    @GetMapping
    public NaverBookApiResponse searchBook(@RequestHeader("Cookie") String cookie, @RequestParam("query") String query, @RequestParam("display") int display, @RequestParam("start") int start, @RequestParam("sort") String sort) {
        String authorization = extractAuthorizationToken(cookie);
        log.info("searchBook - api : {}", authorization);
        Long kakaoId = jwtService.getKakaoId(authorization);

        return bookService.searchBook(query, display, start, sort);
    }

    private String extractAuthorizationToken(String cookie) {
        if (cookie != null && cookie.contains("Authorization=")) {
            return cookie.split("Authorization=")[1].split(";")[0];
        }
        return null;
    }

}
