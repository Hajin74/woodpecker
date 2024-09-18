package org.example.woodpeckerback.exception;

import graphql.ErrorClassification;

public class CustomException extends GraphQLException {

    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    @Override
    public ErrorClassification getErrorType() {
        return ErrorType.CustomException;
    }

}
