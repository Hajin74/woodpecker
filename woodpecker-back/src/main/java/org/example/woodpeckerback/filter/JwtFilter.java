package org.example.woodpeckerback.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.woodpeckerback.dto.CustomOAuth2User;
import org.example.woodpeckerback.entity.User;
import org.example.woodpeckerback.service.JwtService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestUri = request.getRequestURI();
        log.info("jwtFilter URI: {}", requestUri);

        String authorization = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            log.info("cookie: {}, {}", cookie.getName(), cookie.getValue());
            if (cookie.getName().equals("Authorization")) {
                authorization = cookie.getValue();
            }
        }

        if (authorization == null) {
            log.info("Authorization is null");
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization;
        log.info("JwtFilter token: {}", token);

        if (jwtService.isExpired(token)) {
            log.info("Token is expired");
            filterChain.doFilter(request, response);
            return;
        }

        Long id = jwtService.getId(token);
        Long kakaoId = jwtService.getKakaoId(token);
        String username = jwtService.getUsername(token);

        User user = User.builder()
                .id(id)
                .kakaoId(kakaoId)
                .username(username)
                .build();

        log.info("jwtFilter User: {}", user.toString());

        CustomOAuth2User customOAuth2User = new CustomOAuth2User(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(customOAuth2User, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

}
