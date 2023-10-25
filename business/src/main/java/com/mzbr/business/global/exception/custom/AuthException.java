package com.mzbr.business.global.exception.custom;

import com.mzbr.business.global.exception.ErrorCode;

import lombok.Getter;

@Getter
public class AuthException extends RuntimeException {
	private final ErrorCode errorCode;

	public AuthException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
}
