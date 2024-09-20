package org.example.woodpeckerback.graphql;

import graphql.GraphQLContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.woodpeckerback.dto.LikeBookResponse;
import org.example.woodpeckerback.dto.SaveBookInput;
import org.example.woodpeckerback.dto.SaveBookResponse;
import org.example.woodpeckerback.service.BookService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BookResolver {

    private final BookService bookService;

    @QueryMapping
    public String hello() {
        return "Hello, World!";
    }

    @MutationMapping
    public SaveBookResponse saveBook(@Argument("input") SaveBookInput input, GraphQLContext context) {
        Long userId = context.get("userId");
        log.info("saveBook - isbn: {}, user: {}", input.getIsbn(), userId);

        bookService.saveBook(userId, input);
        return new SaveBookResponse(true, "(Isbn: " + input.getIsbn() + ") 이 성공적으로 저장되었습니다.");
    }

    @MutationMapping
    public LikeBookResponse likeBook(@Argument("isbn") String isbn, GraphQLContext context) {
        Long userId = context.get("userId");
        log.info("likeBook - {}, user: {}", isbn, userId);

        bookService.likeBook(userId, isbn);
        return new LikeBookResponse(true, "(Isbn: " + isbn + ") 에 좋아요가 성공적으로 처리되었습니다.");
    }

}
