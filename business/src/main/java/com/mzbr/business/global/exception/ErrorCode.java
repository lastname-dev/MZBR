package com.mzbr.business.global.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {

	// JWT TOKEN
	REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "J-001", "Refresh Token 이 만료되었습니다."),
	REFRESH_TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "J-002", "유효하지 않은 Refresh Token 입니다."),
	ACCESS_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "J-003", "Access Token 이 만료되었습니다."),
	ACCESS_TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "J-004", "유효하지 않은 Access Token 입니다."),
	ACCESS_TOKEN_BLACKLIST(HttpStatus.UNAUTHORIZED, "J-005", "블랙리스트로 등록된 Access Token 입니다."),
	ACCESS_TOKEN_NOT_EXIST(HttpStatus.UNAUTHORIZED, "J-006", "Access Token 이 존재하지 않습니다."),
	JWT_TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "J-007", "유효하지 않은 JWT 입니다."),

	// User
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U-001", "존재하지 않는 회원입니다."),
	NICKNAME_DUPLICATE(HttpStatus.CONFLICT, "U-002", "중복된 닉네임 입니다."),
	USER_NOT_EQUAL(HttpStatus.BAD_REQUEST, "U-003", "사용자 정보가 일치하지 않습니다."),
	USER_ALREADY_SIGNUP(HttpStatus.CONFLICT, "U-003", "이미 가입된 회원입니다."),

	// OAuth
	NOT_SUPPORT_LOGIN_EXCEPTION(HttpStatus.BAD_REQUEST, "O-001", "지원하지 않는 로그인 방식입니다."),
	NOT_EXIST_AUTHORIZATION(HttpStatus.UNAUTHORIZED, "O-002", "Authorization Header가 빈값입니다."),
	NOT_VALID_BEARER_GRANT_TYPE(HttpStatus.UNAUTHORIZED, "O-003", "인증 타입이 Bearer 타입이 아닙니다."),
	NOT_ACCESS_TOKEN_TYPE(HttpStatus.UNAUTHORIZED, "O-004", "Access Token이 아닙니다."),
	OAUTH_BAD_REQUEST(HttpStatus.NOT_FOUND, "O-005", "OAuth 인증이 실패하였습니다."),

	// Follow
	FOLLOW_BAD_REQUEST(HttpStatus.BAD_REQUEST, "F-001", "자기 자신을 팔로우 할 수 없습니다."),

	// AWS
	AWS_S3_UPLOAD_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "A-001", "S3 업로드가 실패하였습니다."),

	// Store
	STORE_NOT_FOUND(HttpStatus.NOT_FOUND, "S-001", "존재하지 않는 식당입니다.");

	ErrorCode(HttpStatus httpStatus, String errorCode, String message) {
		this.httpStatus = httpStatus;
		this.code = errorCode;
		this.message = message;
	}

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}
