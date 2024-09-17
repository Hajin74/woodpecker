package org.example.woodpeckerback.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.woodpeckerback.dto.NaverBookApiResponse;
import org.example.woodpeckerback.service.BookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public NaverBookApiResponse searchBook(@RequestParam("query") String query, @RequestParam("display") int display, @RequestParam("start") int start, @RequestParam("sort") String sort) {
        log.info("searchBook - api");
        return bookService.searchBook(query, display, start, sort);
    }

}
