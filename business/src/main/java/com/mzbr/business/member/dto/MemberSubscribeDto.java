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
	private long followeeId;
	private long followerId;

	public static MemberSubscribeDto of(long followeeId, long followerId) {
		return MemberSubscribeDto.builder()
			.followeeId(followeeId)
			.followerId(followerId)
			.build();
	}
}
