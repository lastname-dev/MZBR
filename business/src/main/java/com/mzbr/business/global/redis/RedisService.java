package com.mzbr.business.global.redis;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisService {
	private final Long expiredDate = 86400L;
	private final RedisTemplate<String, Boolean> redisTemplate;

	public boolean isView(long userId, long postId) {
		String key = generateKey(userId, postId);
		return Boolean.TRUE.equals(redisTemplate.hasKey(key));
	}

	public void checkView(long userId, long postId) {
		String key = generateKey(userId, postId);
		redisTemplate.opsForValue().set(key, true, expiredDate, TimeUnit.SECONDS);
	}

	private static String generateKey(long userId, long postId) {
		return userId + "_" + postId;
	}
}
