package org.example.woodpeckerback.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteNoteResponse {
    private boolean success;
    private String message;

    public DeleteNoteResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
