package com.mzbr.business.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mzbr.business.auth.dto.SignUpDto;
import com.mzbr.business.auth.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "00.Auth")
public class AuthController {

	private final AuthService authService;

	@PostMapping("/signup")
	@Operation(summary = "회원 가입", description = "회원 가입한다.")
	public ResponseEntity<Void> signUp(@RequestBody SignUpDto.Request request) {
		return ResponseEntity.ok().build();
	}
}
