package org.example.woodpeckerback.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.woodpeckerback.dto.CustomOAuth2User;
import org.example.woodpeckerback.dto.KakaoResponse;
import org.example.woodpeckerback.entity.User;
import org.example.woodpeckerback.repository.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 사용자 정보 가져오기
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("oAuth2User: {}", oAuth2User);

        // 소셜 로그인 제공자 확인
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        log.info("registrationId: {}", registrationId);

        // 사용자의 속성 정보 가져오기
        Map<String, Object> attributes = oAuth2User.getAttributes();

        // 속성 정보를 사용하여 KakaoResponse 객체 생성
        KakaoResponse kakaoResponse = new KakaoResponse(attributes);
        Long kakaoId = kakaoResponse.getKakaoId();
        String username = kakaoResponse.getNickName();

        // 신규 회원이면 생성, 기존 회원이면 찾아서 반환
        User user = userRepository.findByKakaoId(kakaoId)
                .orElseGet(() -> userRepository.save(
                        User.builder()
                                .kakaoId(kakaoId)
                                .username(username)
                                .build()
                ));

        return new CustomOAuth2User(user);
    }
}
