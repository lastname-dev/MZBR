package com.mzbr.business.oauth2.kakao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.mzbr.business.global.config.FeignConfig;
import com.mzbr.business.oauth2.kakao.dto.KakaoTokenResponseDto;

@FeignClient(url = "https://kauth.kakao.com/oauth/token", name = "kakaoTokenFeign", configuration = FeignConfig.class)
public interface KakaoTokenFeign {

	@PostMapping(consumes = "application/json")
	KakaoTokenResponseDto.Response requestKakaoToken(
		@RequestParam("grant_type") String grantType,
		@RequestParam("client_id") String clientId,
		@RequestParam("client_secret") String clientSecret,
		@RequestParam("redirect_uri") String redirectUri,
		@RequestParam("code") String code);
}
