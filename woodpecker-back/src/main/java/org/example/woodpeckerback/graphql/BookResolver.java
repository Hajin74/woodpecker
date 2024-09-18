package org.example.woodpeckerback.graphql;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.woodpeckerback.dto.NaverBookItem;
import org.example.woodpeckerback.dto.SaveBookInput;
import org.example.woodpeckerback.dto.SaveBookResponse;
import org.example.woodpeckerback.service.BookService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
//@Component
@RequiredArgsConstructor
public class BookResolver {

    private final BookService bookService;

    @QueryMapping
    public String hello() {
        return "Hello, World!";
    }

    @MutationMapping
    public SaveBookResponse saveBook(@Argument("input") SaveBookInput input) {
        log.info("saveBook - {}", input.getIsbn());
        boolean saved = bookService.saveBook(2L, new NaverBookItem(input.getIsbn(), input.getTitle(), input.getAuthor(), input.getDescription(), input.getPublisher()));
        if (saved) {
            return new SaveBookResponse(true, "success!");
        } else {
            return new SaveBookResponse(false, "already saved");
        }
    }

}
