package org.example.woodpeckerback.dto;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class KakaoResponse {

    public final Map<String, Object> attributes;

    public Long getKakaoId() {
        return Long.valueOf(attributes.get("id").toString());
    }

    public String getNickName() {
        Map<String, Object> profile = (Map<String, Object>) attributes.get("properties");
        return profile != null ? profile.get("nickname").toString() : null;
    }

}
