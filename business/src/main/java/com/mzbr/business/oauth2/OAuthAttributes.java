package com.mzbr.business.oauth2;

import java.util.Map;
import java.util.UUID;

import com.mzbr.business.member.entity.Member;
import com.mzbr.business.member.entity.Role;
import com.mzbr.business.member.entity.SocialType;
import com.mzbr.business.oauth2.userinfo.KakaoOAuth2UserInfo;
import com.mzbr.business.oauth2.userinfo.OAuth2UserInfo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuthAttributes {

	private final String nameAttributeKey;
	private final OAuth2UserInfo oauth2UserInfo;

	@Builder
	public OAuthAttributes(String nameAttributeKey, OAuth2UserInfo oauth2UserInfo) {
		this.nameAttributeKey = nameAttributeKey;
		this.oauth2UserInfo = oauth2UserInfo;
	}

	public static OAuthAttributes of(SocialType socialType,
		String userNameAttributeName, Map<String, Object> attributes) {
		return ofKakao(userNameAttributeName, attributes);
	}

	private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
		return OAuthAttributes.builder()
			.nameAttributeKey(userNameAttributeName)
			.oauth2UserInfo(new KakaoOAuth2UserInfo(attributes))
			.build();
	}

	public Member toEntity(SocialType socialType, OAuth2UserInfo oauth2UserInfo) {
		return Member.builder()
			.socialType(socialType)
			.socialId(oauth2UserInfo.getId())
			.email(UUID.randomUUID() + "@mzbr.com")
			.nickname(oauth2UserInfo.getNickname())
			.role(Role.GUEST)
			.build();
	}
}
