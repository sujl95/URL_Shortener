package me.thewing.url_shortening.common.exception;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseStatus {

	NOT_FOUND_SHORT_URL(4001, "해당 URL이 존재하지 않습니다", NOT_FOUND);

	private final int status;
	private final String message;
	private final HttpStatus httpStatus;

}
