package com.mzbr.business.oauth2.kakao.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class KakaoInfoResponseDto {
	@JsonProperty("id")
	private String kakaoId;

	@JsonProperty("kakao_account")
	private KakaoAccount kakaoAccount;

	@JsonProperty("connected_at")
	private LocalDateTime connectedAt;

	@Getter
	public static class KakaoAccount {

		private Profile profile;

		@Getter
		public static class Profile {

			private String nickname;
		}

	}
}
