package com.mzbr.business.oauth2.kakao.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
public class UserOauthInfo {
	String social_id;
	String nickName;
	boolean isUser;
	String ownJwtAccessToken;
	String ownJwtRefreshToken;

	public static UserOauthInfo of(String social_id, String nickName) {
		return UserOauthInfo.builder()
			.social_id(social_id)
			.nickName(nickName)
			.build();
	}
}
