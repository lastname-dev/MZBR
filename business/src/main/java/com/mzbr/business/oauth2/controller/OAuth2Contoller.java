package com.mzbr.business.oauth2.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mzbr.business.oauth2.kakao.KakaoLoginService;
import com.mzbr.business.oauth2.kakao.dto.UserOauthInfo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class OAuth2Contoller {

	private final KakaoLoginService kakaoLoginService;

	@GetMapping("/oauth2/code")
	public ResponseEntity<?> login(@RequestParam String code) {
		log.info("code:{}", code);
		String kakaoAccessToken = kakaoLoginService.getKakaoAccessToken(code);
		UserOauthInfo userInfo = kakaoLoginService.getUserInfo(kakaoAccessToken);

		return ResponseEntity.ok(userInfo);
	}
}
