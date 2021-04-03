package me.thewing.url_shortening.exception;

import me.thewing.url_shortening.common.exception.ExceptionStatus;
import me.thewing.url_shortening.common.exception.UrlShortenException;

public class NotFoundShortUrlException extends UrlShortenException {
	public NotFoundShortUrlException() {
		super(ExceptionStatus.NOT_FOUND_SHORT_URL);
	}
}
