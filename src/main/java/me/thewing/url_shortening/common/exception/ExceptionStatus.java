package me.thewing.url_shortening.common.exception;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionStatus {

	NOT_FOUND_SHORT_URL(404, "해당 URL이 존재하지 않습니다", NOT_FOUND),
	ORIGIN_URL_NOT_MATCH_PROTOCOL(400, "http:// or https:// 로 시작해야합니다", BAD_REQUEST);

	private final int status;
	private final String message;
	private final HttpStatus httpStatus;

}
