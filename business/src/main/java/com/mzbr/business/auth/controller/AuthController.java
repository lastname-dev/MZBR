package com.mzbr.business.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mzbr.business.auth.dto.SignUpDto;
import com.mzbr.business.auth.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "00.Auth")
public class AuthController {

	private final AuthService authService;

	@PostMapping("/join")
	@Operation(summary = "회원 가입", description = "회원 가입한다.")
	public ResponseEntity<Void> signUp(@RequestBody SignUpDto.Request request,
		@AuthenticationPrincipal UserDetails userDetails) {
		authService.signup(SignUpDto.of(Integer.parseInt(userDetails.getUsername()), request.getNickname()));
		return ResponseEntity.ok().build();
	}
}
