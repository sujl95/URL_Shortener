package me.thewing.url_shortening.controller;

import java.net.URI;
import java.net.URISyntaxException;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import me.thewing.url_shortening.domain.Url;
import me.thewing.url_shortening.service.UrlService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class UrlShortenerController {

	private final UrlService urlService;

	@GetMapping
	public ResponseEntity<String> create(@RequestParam @Valid @NotBlank String url) throws URISyntaxException {
		Url urlInfo = urlService.save(url);
		URI uri = new URI("/api/url/" + urlInfo.getShortUrl());
		return ResponseEntity.created(uri).body(urlInfo.getShortUrl());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findByShortUrl(@PathVariable String id) throws URISyntaxException {
		Url shortUrlInfo = urlService.findByShortUrl(id);
		URI redirectUri = new URI(shortUrlInfo.getOriginUrl());
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(redirectUri);

		return ResponseEntity
				.status(HttpStatus.SEE_OTHER)
				.headers(httpHeaders)
				.build();
	}
}
