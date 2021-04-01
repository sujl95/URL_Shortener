package me.thewing.url_shortening.common.exception;

import lombok.Getter;

@Getter
public class UrlShortenException extends RuntimeException{
	private final ResponseStatus responseStatus;

	public UrlShortenException(ResponseStatus responseStatus) {
		super(responseStatus.getMessage());
		this.responseStatus = responseStatus;
	}
}
