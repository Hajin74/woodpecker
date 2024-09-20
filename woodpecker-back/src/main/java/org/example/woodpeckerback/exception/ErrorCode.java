package org.example.woodpeckerback.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    INVALID_JWT_TOKEN("유효하지 않은 토큰입니다."),
    USER_NOT_FOUND("해당 사용자는 존재하지 않습니다."),
    BOOK_NOT_FOUND("해당 책은 존재하지 않습니다."),
    BOOK_NOT_SAVED("저장한 책이 아닙니다."),
    BOOK_ALREADY_LIKED("이미 좋아요한 책입니다."),
    BOOK_ALREADY_SAVED("이미 저장된 책입니다."),
    NOTE_NOT_FOUND("해당 노트는 존재하지 않습니다."),
    NOTE_ACCESS_DENIED("해당 노트에 접근할 권한이 없습니다.");

    private final String errorMessage;

    ErrorCode(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
