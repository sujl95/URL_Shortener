package me.thewing.url_shortening.exception;

import me.thewing.url_shortening.common.exception.ExceptionStatus;
import me.thewing.url_shortening.common.exception.UrlShortenException;

public class OriginUrlProtocolException extends UrlShortenException {
	public OriginUrlProtocolException() {
		super(ExceptionStatus.ORIGIN_URL_NOT_MATCH_PROTOCOL);
	}
}
