package com.mzbr.business.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class MemberNicknameCheckDto {

	// @Schema(title = "닉네임 중복 검사 요청 DTO")
	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class Request {
		private String nickname;
	}

	// @Schema(title = "닉네임 중복 검사 응답 DTO")
	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor
	@Builder
	public static class Response {
		private boolean isDuplicated;

		public static Response of(boolean isDuplicated) {
			return Response.builder()
				.isDuplicated(isDuplicated)
				.build();
		}
	}
}
