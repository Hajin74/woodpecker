package org.example.woodpeckerback.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveNoteInput {
    private String isbn;
    private String content;
}
