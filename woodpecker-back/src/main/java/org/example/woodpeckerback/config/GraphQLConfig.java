package org.example.woodpeckerback.config;

import graphql.GraphQLError;
import graphql.schema.DataFetchingEnvironment;
import org.example.woodpeckerback.exception.CustomException;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;

@Configuration
public class GraphQLConfig extends DataFetcherExceptionResolverAdapter {
    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        return ex instanceof CustomException ?
                (GraphQLError) (new CustomException(ex.getMessage())) : super.resolveToSingleError(ex, env);
    }
}
