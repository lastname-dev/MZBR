package com.mzbr.business.video.dto;

import java.util.List;

import com.mzbr.business.video.entity.Video;
import com.mzbr.business.video.entity.VideoData;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VideoDto {
	Long id;
	String videoUuid;
	String thumbnailUrl;
	String writer;
	int star;
	String description;
	int views;

	public static VideoDto from(Video video) {
		VideoData videoData = video.getVideoData();
		return VideoDto.builder()
			.id(video.getId())
			.videoUuid(video.getVideoUuid())
			.thumbnailUrl(video.getThumbnailUrl())
			.writer(video.getMember().getNickname())
			.star(videoData.getStar())
			.description(videoData.getDescription())
			.views(videoData.getViews())
			.build();
	}

	@Builder
	@Getter
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class Response {
		List<VideoDto> videoDtos;

		public static Response from(List<VideoDto> videoDtos) {
			return Response.builder()
				.videoDtos(videoDtos)
				.build();
		}
	}
}
