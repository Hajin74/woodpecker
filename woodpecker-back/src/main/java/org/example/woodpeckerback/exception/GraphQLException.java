package org.example.woodpeckerback.exception;

import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.language.SourceLocation;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class GraphQLException extends RuntimeException implements GraphQLError {

    public String message;

    public GraphQLException(String message) {
        super(message);
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    @Override
    public ErrorClassification getErrorType() {
        return null;
    }

    @Override
    public Map<String, Object> getExtensions() {
        return Map.of(
                "exception", this.getClass().getName()
        );
    }

}
