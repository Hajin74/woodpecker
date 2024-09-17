package org.example.woodpeckerback.dto;

public record NaverBookItem(
        String title,
        String author,
        String publisher,
        String description,
        String isbn
) {
}
