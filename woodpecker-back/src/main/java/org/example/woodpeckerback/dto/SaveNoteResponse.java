package org.example.woodpeckerback.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveNoteResponse {
    private boolean success;
    private String message;

    public SaveNoteResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
