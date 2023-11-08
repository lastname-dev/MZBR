package com.mzbr.business.global.exception.custom;

import com.mzbr.business.global.exception.ErrorCode;

import lombok.Getter;

@Getter
public class AWSException extends RuntimeException {
	private final ErrorCode errorCode;

	public AWSException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
}

