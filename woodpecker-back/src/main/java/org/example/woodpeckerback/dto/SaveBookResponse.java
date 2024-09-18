package org.example.woodpeckerback.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveBookResponse {
    private boolean success;
    private String message;

    public SaveBookResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}

