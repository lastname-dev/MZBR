package com.mzbr.business.oauth2.service;

import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.mzbr.business.member.entity.Member;
import com.mzbr.business.member.entity.SocialType;
import com.mzbr.business.member.repository.MemberRepository;
import com.mzbr.business.oauth2.CustomOAuth2User;
import com.mzbr.business.oauth2.OAuthAttributes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

	private final MemberRepository memberRepository;

	private static final String NAVER = "naver";
	private static final String KAKAO = "kakao";

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
		OAuth2User oAuth2User = delegate.loadUser(userRequest);

		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		SocialType socialType = getSocialType(registrationId);
		String userNameAttributeName = userRequest.getClientRegistration()
			.getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
		Map<String, Object> attributes = oAuth2User.getAttributes();

		OAuthAttributes extractAttributes = OAuthAttributes.of(socialType, userNameAttributeName, attributes);
		Member member = getMember(extractAttributes, socialType);

		return new CustomOAuth2User(
			Collections.singleton(new SimpleGrantedAuthority(member.getRole().getKey())),
			attributes,
			extractAttributes.getNameAttributeKey(),
			member.getEmail(),
			member.getRole()
		);
	}

	private SocialType getSocialType(String registrationId) {
		if (NAVER.equals(registrationId)) {
			return SocialType.NAVER;
		}
		if (KAKAO.equals(registrationId)) {
			return SocialType.KAKAO;
		}
		return SocialType.GOOGLE;
	}

	private Member getMember(OAuthAttributes attributes, SocialType socialType) {
		Member member = memberRepository.findBySocialTypeAndSocialId(socialType,
			attributes.getOauth2UserInfo().getId()).orElse(null);

		if (member == null) {
			return saveMember(attributes, socialType);
		}
		return member;
	}

	private Member saveMember(OAuthAttributes attributes, SocialType socialType) {
		Member member = attributes.toEntity(socialType, attributes.getOauth2UserInfo());
		return memberRepository.save(member);
	}
}
