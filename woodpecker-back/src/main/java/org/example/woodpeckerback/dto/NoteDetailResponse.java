package org.example.woodpeckerback.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NoteDetailResponse {

    private Long noteId;
    private Long userId;
    private Long bookId;
    private String content;

}
