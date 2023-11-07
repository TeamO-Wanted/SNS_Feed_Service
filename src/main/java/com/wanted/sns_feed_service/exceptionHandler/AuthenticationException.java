package com.wanted.sns_feed_service.exceptionHandler;

public class AuthenticationException extends RuntimeException {
	public AuthenticationException(String message) {
		super(message);
	}
}