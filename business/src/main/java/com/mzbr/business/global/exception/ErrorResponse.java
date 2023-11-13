package com.mzbr.business.global.exception;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {

	private static final ObjectMapper objectMapper = new ObjectMapper();
	private final String timeStamp = String.valueOf(LocalDateTime.now());
	private final int status;
	private final String errorCode;
	private final String errorMessage;
	private final String path;

	public static ErrorResponse of(HttpStatus httpStatus, String errorMessage) {
		return ErrorResponse.builder()
			.status(httpStatus.value())
			.errorCode(httpStatus.getReasonPhrase())
			.errorMessage(errorMessage)
			.build();
	}

	public static ErrorResponse of(HttpStatus httpStatus, String errorMessage, HttpServletRequest request) {
		return ErrorResponse.builder()
			.status(httpStatus.value())
			.errorCode(httpStatus.getReasonPhrase())
			.errorMessage(errorMessage)
			.path(request.getRequestURI())
			.build();
	}

	public static ErrorResponse from(ErrorCode errorCode) {
		return ErrorResponse.builder()
			.status(errorCode.getHttpStatus().value())
			.errorCode(errorCode.getCode())
			.errorMessage(errorCode.getMessage())
			.build();
	}

	public String convertToJson() throws JsonProcessingException {
		return objectMapper.writeValueAsString(this);
	}

}