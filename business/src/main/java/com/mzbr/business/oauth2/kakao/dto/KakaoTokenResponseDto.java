package com.mzbr.business.oauth2.kakao.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class KakaoTokenResponseDto {
	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
	public static class Request {
		private final String grantType = "authorization_code";
		private String clientId;
		private String clientSecret;
		private String redirectUri;
		private String code;

		@Builder
		public Request(String clientId, String clientSecret, String redirectUri, String code) {
			this.clientId = clientId;
			this.clientSecret = clientSecret;
			this.redirectUri = redirectUri;
			this.code = code;
		}

		public static Request of(String clientId, String clientSecret, String redirectUri, String code) {
			return Request.builder()
				.clientId(clientId)
				.clientSecret(clientSecret)
				.redirectUri(redirectUri)
				.code(code)
				.build();
		}
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
	public static class Response {
		private String tokenType;
		private String accessToken;
		private Integer expiresIn;
		private String refreshToken;
		private Integer refreshTokenExpiresIn;
		private String scope;

		@Builder
		public Response(String tokenType, String accessToken, Integer expiresIn, String refreshToken,
			Integer refreshTokenExpiresIn, String scope) {
			this.tokenType = tokenType;
			this.accessToken = accessToken;
			this.expiresIn = expiresIn;
			this.refreshToken = refreshToken;
			this.refreshTokenExpiresIn = refreshTokenExpiresIn;
			this.scope = scope;
		}
	}
}
