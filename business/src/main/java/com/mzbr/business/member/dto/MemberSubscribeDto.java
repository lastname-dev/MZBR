package com.mzbr.business.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberSubscribeDto {
	private int followeeId;
	private int followerId;

	public static MemberSubscribeDto of(int followeeId, int followerId) {
		return MemberSubscribeDto.builder()
			.followeeId(followeeId)
			.followerId(followerId)
			.build();
	}
}
