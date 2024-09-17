package org.example.woodpeckerback.dto;

public record NaverBookResponse(
        Long isbn,
        String title,
        String author,
        String publisher,
        String description) {
}
