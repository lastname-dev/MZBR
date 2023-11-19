package com.mzbr.business.member.dto;

import com.mzbr.business.member.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDto {
	private long userId;
	private String profileImage;
	private String nickname;
	private int subscribeCount;
	private int postCount;

	public static MemberDto from(Member member) {
		return MemberDto.builder()
			.userId(member.getId())
			.nickname(member.getNickname())
			.profileImage(member.getProfileImage())
			.build();
	}

	public static MemberDto of(Member member, long postCount, long subscribeCount) {
		return MemberDto.builder()
			.userId(member.getId())
			.nickname(member.getNickname())
			.postCount((int)postCount)
			.subscribeCount((int)subscribeCount)
			.profileImage(member.getProfileImage())
			.build();
	}

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class Response {
		private long userId;

		private String profileImage;
		private String nickname;

		private int subscribeCount;
		private int postCount;

		public static Response from(MemberDto member) {
			return Response.builder()
				.userId(member.getUserId())
				.nickname(member.getNickname())
				.profileImage(member.getProfileImage())
				.build();
		}
	}

}
