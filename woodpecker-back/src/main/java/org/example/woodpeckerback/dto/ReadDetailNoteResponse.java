package org.example.woodpeckerback.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReadDetailNoteResponse {
    private boolean success;
    private NoteDetailResponse noteDetailResponse;

    public ReadDetailNoteResponse(boolean success, NoteDetailResponse noteDetailResponse) {
        this.success = success;
        this.noteDetailResponse = noteDetailResponse;
    }
}
