package org.example.woodpeckerback.dto;

import lombok.RequiredArgsConstructor;
import org.example.woodpeckerback.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import java.util.Collection;
import java.util.Map;

@RequiredArgsConstructor
public class CustomOAuth2User implements OAuth2User {

    private final User user;

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getName() {
        return user.getUsername();
    }

    public Long getKakaoId() { // 카카오에서 주는 아이디
        return user.getKakaoId();
    }

    public Long getId() { // DB 에서 생성되는 아이디
        return user.getId();
    }

}
