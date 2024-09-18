package org.example.woodpeckerback.exception;

public enum ErrorCode {
    USER_NOT_FOUND("해당 사용자는 존재하지 않습니다."),
    BOOK_NOT_FOUND("해당 책은 존재하지 않습니다."),
    BOOK_ALREADY_LIKED("이미 좋아요한 책입니다.");

    private final String errorMessage;

    ErrorCode(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
