package me.thewing.url_shortening.service;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.thewing.url_shortening.common.utils.EncoderBase62;
import me.thewing.url_shortening.domain.Url;
import me.thewing.url_shortening.exception.NotFoundShortUrlException;

@Service
@Slf4j
@RequiredArgsConstructor
public class UrlService {

	private final EncoderBase62 encoderBase62;
	private final HashMap<String, Url> idDump;
	private final HashMap<String, Url> urlDump;

	private long generateId;

	public Url save(String originUrl) {
		if (isExistByUrl(originUrl)) {
			return urlDump.get(originUrl);
		} else {
			return store(originUrl);
		}
	}

	public boolean isExistByUrl(String url) {
		return urlDump.containsKey(url);
	}
	private Url store(String url) {
		Url urlInfo;
		try {
			generateId++;
			log.info("id -> {}", generateId);
			urlInfo = Url.builder()
					.id(generateId)
					.shortUrl(encoderBase62.urlEncoder(url,generateId))
					.build();
			idDump.put(String.valueOf(generateId), urlInfo);
			urlDump.put(urlInfo.getShortUrl(), urlInfo);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("Exception message");
		}
		return urlInfo;
	}

	public Url findByShortUrl(String shortUrl) {
		log.info("urlDump -> {}", urlDump);
		System.out.println(shortUrl);
		if (urlDump.get(shortUrl) == null) {
			throw new NotFoundShortUrlException();
		}
		return urlDump.get(shortUrl);
	}
}
