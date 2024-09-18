package org.example.woodpeckerback.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeBookResponse {
    private boolean success;
    private String message;

    public LikeBookResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
