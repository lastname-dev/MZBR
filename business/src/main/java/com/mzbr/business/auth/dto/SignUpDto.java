package com.mzbr.business.auth.dto;

import javax.validation.constraints.NotEmpty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
public class SignUpDto {

	private final int userId;
	private final String nickname;

	public static SignUpDto of(int userId, String nickname) {
		return SignUpDto.builder()
			.userId(userId)
			.nickname(nickname)
			.build();
	}

	@Schema(title = "회원 가입 요청 DTO")
	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class Request {

		@Schema(description = "회원 가입 닉네임", example = "홍길동")
		@NotEmpty(message = "닉네임은 필수 항목입니다.")
		private String nickname;
	}

}
