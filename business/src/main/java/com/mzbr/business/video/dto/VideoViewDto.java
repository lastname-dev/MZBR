package com.mzbr.business.video.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class VideoViewDto {
	long memberId;
	long videoId;

	public static VideoViewDto of(long memberId, int videoId) {
		return VideoViewDto.builder()
			.memberId(memberId)
			.videoId(videoId)
			.build();
	}
}
