package com.mzbr.business.member.dto;

import javax.validation.constraints.NotEmpty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
public class MemberNicknameChangeDto {
	private long userId;
	private String nickname;

	public static MemberNicknameChangeDto of(int userId, String nickname) {
		return MemberNicknameChangeDto.builder()
			.userId(userId)
			.nickname(nickname)
			.build();
	}

	@Schema(title = "사용자 닉네임 변경 Request ", name = "닉네임 변경 요청")
	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class Request {
		@Schema(description = "변경할 닉네임", example = "홍길동")
		@NotEmpty(message = "닉네임은 필수 항목입니다.")
		String nickname;
	}
}
