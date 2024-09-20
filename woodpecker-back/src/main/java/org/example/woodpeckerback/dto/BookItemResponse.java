package org.example.woodpeckerback.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BookItemResponse {
    private Long id;
    private String title;
    private String author;
    private String publisher;
    private String description;
    private String isbn;
}
