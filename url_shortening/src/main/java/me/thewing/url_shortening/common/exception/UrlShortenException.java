package me.thewing.url_shortening.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class UrlShortenException extends RuntimeException{
	private final ExceptionStatus responseStatus;

	public UrlShortenException(ExceptionStatus responseStatus) {
		super(responseStatus.getMessage());
		this.responseStatus = responseStatus;
	}

	public UrlShortenException(String message, ExceptionStatus responseStatus) {
		super(message);
		this.responseStatus = responseStatus;
	}

	public HttpStatus getHttpStatus() {
		return responseStatus.getHttpStatus();
	}

	public String getStatus() {
		return String.valueOf(responseStatus.getStatus());
	}
}
