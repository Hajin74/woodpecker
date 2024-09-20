package org.example.woodpeckerback.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.example.woodpeckerback.exception.CustomException;
import org.example.woodpeckerback.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.server.WebGraphQlInterceptor;
import org.springframework.graphql.server.WebGraphQlRequest;
import org.springframework.graphql.server.WebGraphQlResponse;
import reactor.core.publisher.Mono;
import org.springframework.http.HttpHeaders;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Slf4j
@Configuration
public class JwtInterceptor implements WebGraphQlInterceptor {

    private SecretKey secretKey;

    public JwtInterceptor(@Value("${SECRET}") String secret) {
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    @Override
    public Mono<WebGraphQlResponse> intercept(WebGraphQlRequest request, Chain chain) {
        log.info("request getDocument = {}", request.getDocument());
        log.info("request getHeaders = {}", request.getHeaders());

        HttpHeaders headers = request.getHeaders();
        String authorization = extractAuthorizationToken(headers.getFirst(HttpHeaders.COOKIE));

        try {
            // JWT 토큰 검증
            Claims claims = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(authorization)
                    .getPayload();

            log.info("JWT claims: {}", claims);

            // 사용자 정보 attributes 에 저장
            request.configureExecutionInput((executionInput, builder) ->
                    builder.graphQLContext(ctx -> ctx.put("userId", claims.get("id", Long.class)))
                    .build()
            );

            // 토큰이 유효한 경우 요청을 계속 진행
            return chain.next(request);
        } catch (Exception e) {
            // 토큰 검증 실패 시 예외 처리
            log.error("Invalid JWT token", e);
            throw new CustomException(ErrorCode.INVALID_JWT_TOKEN);
        }
    }

    private String extractAuthorizationToken(String cookie) {
        if (cookie != null && cookie.contains("Authorization=")) {
            return cookie.split("Authorization=")[1].split(";")[0];
        }
        return null;
    }

}
