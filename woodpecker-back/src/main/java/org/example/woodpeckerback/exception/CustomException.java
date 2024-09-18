package org.example.woodpeckerback.exception;

import graphql.ErrorClassification;

public class CustomException extends GraphQLException {
    public String message;

    public CustomException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public ErrorClassification getErrorType() {
        return ErrorType.CustomException;
    }

}
