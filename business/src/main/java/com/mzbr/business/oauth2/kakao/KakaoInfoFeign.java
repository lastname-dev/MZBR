package com.mzbr.business.oauth2.kakao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.mzbr.business.global.config.FeignConfig;
import com.mzbr.business.oauth2.kakao.dto.KakaoInfoResponseDto;

@FeignClient(url = "https://kapi.kakao.com/v2/user/me", name = "kakaoInfoClient", configuration = FeignConfig.class)
public interface KakaoInfoFeign {
	@GetMapping(consumes = "application/json")
	KakaoInfoResponseDto getKakaoMemberInfo(
		@RequestHeader("Authorization") String accessToken);
}
