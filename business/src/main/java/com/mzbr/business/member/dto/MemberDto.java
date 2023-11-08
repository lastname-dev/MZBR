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
	private int userId;
	private String profileImage;
	private String nickname;

	public static MemberDto from(Member member) {
		return MemberDto.builder()
			.userId(member.getId())
			.nickname(member.getNickname())
			.profileImage(member.getProfileImage())
			.build();
	}
}
