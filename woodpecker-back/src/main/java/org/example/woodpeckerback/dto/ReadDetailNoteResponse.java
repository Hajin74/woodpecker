package org.example.woodpeckerback.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReadDetailNoteResponse {
    private boolean success;
    private String message;

    public ReadDetailNoteResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
