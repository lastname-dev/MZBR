package com.mzbr.business.auth.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
public class SignUpDto {

	private final String userId;
	private final String nickname;

	public static SignUpDto of(String userId, String nickname) {
		return SignUpDto.builder()
			.userId(userId)
			.nickname(nickname)
			.build();
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class Request {
		private String nickname;
	}

}
