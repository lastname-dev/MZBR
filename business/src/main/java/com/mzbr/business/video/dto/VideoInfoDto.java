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
	Long id;
	String videoUuid;
	String thumbnailUrl;
	String masterUrl;
	String storeName;
	String writer;
	int star;
	String description;
	int views;

	public static VideoInfoDto from(Video video) {
		VideoData videoData = video.getVideoData();
		return VideoInfoDto.builder()
			.id(video.getId())
			.videoUuid(video.getVideoUuid())
			.thumbnailUrl(video.getThumbnailUrl())
			.masterUrl(videoData.getMasterUrl())
			.storeName(video.getStore().getName())
			.writer(video.getMember().getNickname())
			.star(videoData.getStar())
			.description(videoData.getDescription())
			.views(videoData.getViews())
			.build();
	}
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	@Getter
	public static class Response {
		Long id;
		String videoUuid;
		String thumbnailUrl;
		String masterUrl;
		String storeName;
		String writer;
		int star;
		String description;
		int views;
		public static Response from(VideoInfoDto video) {
			return Response.builder()
				.id(video.getId())
				.videoUuid(video.getVideoUuid())
				.thumbnailUrl(video.getThumbnailUrl())
				.masterUrl(video.getMasterUrl())
				.storeName(video.getStoreName())
				.writer(video.getWriter())
				.star(video.getStar())
				.description(video.getDescription())
				.views(video.getViews())
				.build();
		}
	}

}
