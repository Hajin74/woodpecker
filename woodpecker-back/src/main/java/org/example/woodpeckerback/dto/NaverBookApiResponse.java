package org.example.woodpeckerback.dto;

import java.util.List;

public record NaverBookApiResponse(
        Integer total,
       Integer start,
       Integer display,
       List<NaverBookItem> items) {
}
