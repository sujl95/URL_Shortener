package me.thewing.url_shortening.controller.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.thewing.url_shortening.common.exception.UrlShortenException;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionResponseInfo {
	private final String status;
	private final String message;

	public static ExceptionResponseInfo of(UrlShortenException urlShortenException) {
		return new ExceptionResponseInfo(urlShortenException.getStatus(), urlShortenException.getMessage());
	}
}
