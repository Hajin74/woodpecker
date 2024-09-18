package org.example.woodpeckerback.exception;

import graphql.ErrorClassification;
import lombok.Getter;

@Getter
public class CustomException extends GraphQLException {

    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
    }

    @Override
    public ErrorClassification getErrorType() {
        return ErrorType.CustomException;
    }

}
