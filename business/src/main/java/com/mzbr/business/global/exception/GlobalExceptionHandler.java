package com.mzbr.business.global.exception;

import javax.naming.SizeLimitExceededException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.mzbr.business.global.exception.custom.AuthException;
import com.mzbr.business.global.exception.custom.BadRequestException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(BadRequestException.class)
	protected ResponseEntity<ErrorResponse> handleConflict(BadRequestException badRequestException) {
		log.error("BadRequestException : { }", badRequestException);
		return handleExceptionInternal(badRequestException.getErrorCode());
	}

	@ExceptionHandler(AuthException.class)
	protected ResponseEntity<ErrorResponse> handleConflict(AuthException authException) {
		log.error("AuthException : { }", authException);
		return handleExceptionInternal(authException.getErrorCode());
	}

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ErrorResponse> handleException(Exception exception) {
		log.error("Exception : { }", exception);
		ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR,
			"서버 오류가 발생하였습니다.");
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	}

	private ResponseEntity<ErrorResponse> handleExceptionInternal(ErrorCode errorCode) {
		return ResponseEntity
			.status(errorCode.getHttpStatus().value())
			.body(ErrorResponse.from(errorCode));
	}
}
