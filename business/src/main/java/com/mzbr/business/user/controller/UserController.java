package com.mzbr.business.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mzbr.business.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "01.User")
public class UserController {
	private final UserService userService;

	@GetMapping("/test")
	@Operation(summary = "사용자 등록", description = "사용자를 등록한다.", responses = {
		@ApiResponse(responseCode = "200", description = "사용자 등록에 성공했습니다."),
		@ApiResponse(responseCode = "500", description = "사용자 등록 과정에서 오류가 발생했습니다.")})
	public ResponseEntity<?> test() {
		return ResponseEntity.ok("zzz");
	}

}
