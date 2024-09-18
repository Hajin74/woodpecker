package org.example.woodpeckerback.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.woodpeckerback.dto.NaverBookApiResponse;
import org.example.woodpeckerback.dto.NaverBookItem;
import org.example.woodpeckerback.entity.User;
import org.example.woodpeckerback.service.BookService;
import org.example.woodpeckerback.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<NaverBookApiResponse> searchBook(@RequestHeader("Cookie") String cookie, @RequestParam("query") String query, @RequestParam("display") int display, @RequestParam("start") int start, @RequestParam("sort") String sort) {
        String authorization = extractAuthorizationToken(cookie);
        Long kakaoId = jwtService.getKakaoId(authorization);
        log.info("searchBook - api : {}", kakaoId);
        NaverBookApiResponse response = bookService.searchBook(query, display, start, sort);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<String> saveBook(@RequestHeader("Cookie") String cookie, @RequestBody NaverBookItem naverBookItem) {
        String authorization = extractAuthorizationToken(cookie);
        Long userId = jwtService.getId(authorization);
        log.info("saveBook - api : {}", userId);

        if (bookService.saveBook(userId, naverBookItem)) {
            return ResponseEntity.ok("success!");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("already saved");
        }
    }

    private String extractAuthorizationToken(String cookie) {
        if (cookie != null && cookie.contains("Authorization=")) {
            return cookie.split("Authorization=")[1].split(";")[0];
        }
        return null;
    }

}
