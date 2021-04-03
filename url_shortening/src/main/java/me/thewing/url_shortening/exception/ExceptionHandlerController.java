package me.thewing.url_shortening.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;
import me.thewing.url_shortening.common.exception.UrlShortenException;
import me.thewing.url_shortening.controller.response.ExceptionResponseInfo;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerController {

	@ExceptionHandler(UrlShortenException.class)
	private ResponseEntity<ExceptionResponseInfo> handleStatusException(UrlShortenException exception) {
		ExceptionResponseInfo response = ExceptionResponseInfo.of(exception);
		errorLogging(exception);
		HttpStatus httpStatus = exception.getHttpStatus();
		return new ResponseEntity<>(response, httpStatus);
	}

	private void errorLogging(Exception ex) {
		log.error("Exception = {} , message = {}", ex.getClass().getSimpleName(),
				ex.getLocalizedMessage());
	}

}
