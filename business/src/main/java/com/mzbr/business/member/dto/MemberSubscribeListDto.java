package com.mzbr.business.member.dto;

import java.util.List;

import com.mzbr.business.member.entity.Member;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class MemberSubscribeListDto {

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor
	@Builder
	public static class Response {
		private List<MemberDto> subscribes;

		public static Response from(List<MemberDto> memberDtos) {
			return Response.builder()
				.subscribes(memberDtos)
				.build();
		}
	}
}
