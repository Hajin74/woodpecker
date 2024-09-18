package org.example.woodpeckerback.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveBookInput {
    private String isbn;
    private String title;
    private String author;
    private String description;
    private String publisher;
}
