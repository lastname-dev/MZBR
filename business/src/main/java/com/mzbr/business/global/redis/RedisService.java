package com.mzbr.business.global.redis;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.mzbr.business.video.dto.VideoViewDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisService {
	private final Long expiredDate = 86400L;
	private final String VIDEO_PREFIX = "video";
	private final RedisTemplate<String, Boolean> redisTemplate;

	public boolean isView(VideoViewDto videoViewDto) {
		String key = generateKey(videoViewDto.getMemberId(), videoViewDto.getVideoId());
		return Boolean.TRUE.equals(redisTemplate.hasKey(key));
	}

	public void checkView(VideoViewDto videoViewDto) {
		String key = generateKey(videoViewDto.getMemberId(), videoViewDto.getVideoId());
		redisTemplate.opsForValue().set(key, true, expiredDate, TimeUnit.SECONDS);
		if (Boolean.TRUE.equals(redisTemplate.hasKey(VIDEO_PREFIX + videoViewDto.getVideoId()))) {
			
		}
	}

	private static String generateKey(long memberId, long videoId) {
		return memberId + "_" + videoId;
	}
}
