package com.mzbr.business.global.jwt;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.mzbr.business.global.exception.ErrorCode;
import com.mzbr.business.global.exception.custom.AuthException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Getter
public class JwtService {
	@Value("${jwt.access.header}")
	private String ACCESS_HEADER;

	@Value("${jwt.refresh.header}")
	private String REFRESH_HEADER;

	@Value("${jwt.access.expiration}")
	private int ACCESS_EXPIRATION_TIME;

	@Value("${jwt.refresh.expiration}")
	private int REFRESH_EXPIRATION_TIME;

	@Value("${jwt.secretKey}")
	private String secretKey;

	private static final String PREFIX = "Bearer ";
	private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
	private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
	private static final String EXPIRED_TOKEN_SUBJECT = "ExpiredToken";

	private final RedisTemplate redisTemplate;

	public String createAccessToken(int id) {
		return JWT.create()
			.withSubject(ACCESS_TOKEN_SUBJECT)
			.withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_EXPIRATION_TIME))
			.withClaim("id", id)
			.sign(Algorithm.HMAC512(secretKey));
	}

	public String createRefreshToken() {
		return JWT.create()
			.withSubject(REFRESH_TOKEN_SUBJECT)
			.withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION_TIME))
			.sign(Algorithm.HMAC512(secretKey));
	}

}
