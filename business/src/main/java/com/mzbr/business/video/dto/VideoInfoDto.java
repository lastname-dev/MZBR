package com.mzbr.business.video.dto;

import com.mzbr.business.video.entity.Video;
import com.mzbr.business.video.entity.VideoData;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideoInfoDto {
	private Long id;
	private String videoUuid;
	private String thumbnailUrl;
	private String masterUrl;
	private String writer;
	private int star;
	private String description;
	private int views;

	public static VideoInfoDto from(Video video) {
		VideoData videoData = video.getVideoData();
		return VideoInfoDto.builder()
			.id(video.getId())
			.videoUuid(video.getVideoUuid())
			.thumbnailUrl(video.getThumbnailUrl())
			.masterUrl(videoData.getMasterUrl())
			.writer(video.getMember().getNickname())
			.star(videoData.getStar())
			.description(videoData.getDescription())
			.views(videoData.getViews())
			.build();
	}

}
