package me.thewing.url_shortening.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import me.thewing.url_shortening.controller.response.UrlResponse;
import me.thewing.url_shortening.domain.Url;
import me.thewing.url_shortening.dto.IpDto;
import me.thewing.url_shortening.dto.UrlCreateDto;
import me.thewing.url_shortening.exception.NotFoundShortUrlException;
import me.thewing.url_shortening.exception.OriginUrlProtocolException;
import me.thewing.url_shortening.service.UrlService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class UrlShortenerController {

	private final UrlService urlService;

	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody UrlCreateDto url, HttpServletRequest request) throws URISyntaxException {
		if (url.getUrl().startsWith("http://") || url.getUrl().startsWith("https://")) {
			Url urlInfo = urlService.save(url.getUrl(), request);
			URI uri = new URI("/" + urlInfo.getShortUrl());
			UrlResponse urlResponse = new UrlResponse("thewing.kr/" + urlInfo.getShortUrl());
			return ResponseEntity.created(uri).body(urlResponse);
		}
		throw new OriginUrlProtocolException();
	}

	@GetMapping("/{shortUrl}")
	public ResponseEntity<Url> findByShortUrl(@PathVariable String shortUrl, HttpServletRequest request) throws URISyntaxException {
		Url shortUrlInfo = urlService.findByShortUrl(shortUrl, request);
		URI redirectUri = new URI(shortUrlInfo.getOriginUrl());
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(redirectUri);
		return ResponseEntity
				.status(HttpStatus.SEE_OTHER)
				.headers(httpHeaders)
				.build();
	}

	@GetMapping("/logs/{shortUrl}")
	public ResponseEntity<Url> getLogs(@PathVariable String shortUrl) {
		Url logs = urlService.getLogs(shortUrl);
		if (ObjectUtils.isEmpty(logs)) {
			throw new NotFoundShortUrlException();
		}
		return ResponseEntity.status(HttpStatus.OK).body(logs);
	}
}
