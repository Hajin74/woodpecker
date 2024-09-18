package org.example.woodpeckerback.config;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.example.woodpeckerback.exception.CustomException;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;

import java.util.Map;

@Configuration
public class GraphQLConfig extends DataFetcherExceptionResolverAdapter {
    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        if (ex instanceof CustomException customException) {
            return GraphqlErrorBuilder.newError(env)
                    .message(customException.getErrorCode().getErrorMessage())
                    .extensions(Map.of("code", customException.getErrorCode().name()))
                    .build();
        } else {
            return super.resolveToSingleError(ex, env);
        }}
}
