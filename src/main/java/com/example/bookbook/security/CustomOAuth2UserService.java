package com.example.bookbook.security;

import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.example.bookbook.domain.entity.Role;
import com.example.bookbook.domain.entity.UserEntity;
import com.example.bookbook.domain.repository.UserEntityRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

	private final PasswordEncoder passwordEncoder;
    private final UserEntityRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        return socialUser(oAuth2User, registrationId);
    }

    private OAuth2User socialUser(OAuth2User oAuth2User, String registrationId) {
        final String[] email = {null};
        final String[] name = {null};

        if (registrationId.equals("google")) {
            email[0] = oAuth2User.getAttribute("email");
            name[0] = oAuth2User.getAttribute("name");
        } else if (registrationId.equals("naver")) {
            Map<String, Object> response = oAuth2User.getAttribute("response");
            email[0] = (String) response.get("email");
            name[0] = (String) response.get("name");
        } else if (registrationId.equals("kakao")) {
            Map<String, Object> response = oAuth2User.getAttribute("kakao_account");
            @SuppressWarnings("unchecked")
            Map<String, Object> profile = (Map<String, Object>) response.get("profile");
            email[0] = (String) response.get("email");
            name[0] = (String) profile.get("nickname");
        }

        UserEntity entity = userRepository.findByEmail(email[0])
                .orElseGet(() -> createSocialUser(email[0], name[0]));

        return new CustomUserDetails(entity);
    }

    private UserEntity createSocialUser(String email, String name) {
        UserEntity entity = UserEntity.builder()
                .email(email)
                .userName(name)
                .password(passwordEncoder.encode(String.valueOf(System.currentTimeMillis())))
                .userRRN("소셜로그인")
                .gender("미입력")
                .phoneNumber("미입력")
                .birthDate("미입력")
                .postcode("미입력")
                .address("미입력")
                .extraAddress("미입력")
                .detailAddress("미입력")
                .status(1L) // 활성 상태로 가정
                .build()
                .addRole(Role.USER);

        return userRepository.save(entity);
    }
}