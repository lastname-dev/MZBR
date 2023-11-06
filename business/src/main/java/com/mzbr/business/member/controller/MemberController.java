package com.mzbr.business.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mzbr.business.member.dto.MemberNicknameCheckDto;
import com.mzbr.business.member.service.MemberService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "01.User")
public class MemberController {
	private final MemberService memberService;

	@PostMapping("/nickname/check")
	@Operation(summary = "닉네임 중복 검사", description = "닉네임을 중복 검사한다.", responses = {
		@ApiResponse(responseCode = "200", description = "닉네임을 중복 검사 하였습니다."),
		@ApiResponse(responseCode = "500", description = "잘못된 요청 입니다.")})
	public ResponseEntity<MemberNicknameCheckDto.Response> checkNicknameIsPresent(
		@RequestBody MemberNicknameCheckDto.Request request) {
		boolean isPresent = memberService.checkNicknameIsPresent(request.getNickname());
		return ResponseEntity.ok(MemberNicknameCheckDto.Response.of(isPresent));
	}

	// @PatchMapping("/nickname")

}
