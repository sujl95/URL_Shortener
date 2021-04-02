package me.thewing.url_shortening.service;

import java.util.HashMap;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.thewing.url_shortening.common.utils.Sha256;
import me.thewing.url_shortening.domain.Url;
import me.thewing.url_shortening.exception.NotFoundShortUrlException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UrlService {

	private final HashMap<String, Url> urlDump;
	private final HashMap<String, Url> shortUrlDump;

	public Url save(String originUrl) {
		if (isExistByOriginUrl(originUrl)) {
			return urlDump.get(originUrl);
		} else {
			return store(originUrl);
		}
	}

	public boolean isExistByOriginUrl(String url) {
		return urlDump.containsKey(url);
	}
	public boolean isExistByShortUrl(String url) {
		return shortUrlDump.containsKey(url);
	}
	private Url store(String url) {
		int pos = 0;
		String shortUrl = Sha256.encode(url, pos);
		while (isExistByShortUrl(shortUrl)) {
			shortUrl = Sha256.encode(url, ++pos);
		}
		Url urlInfo = Url.builder()
				.shortUrl(shortUrl)
				.originUrl(url)
				.build();
		shortUrlDump.put(urlInfo.getShortUrl(), urlInfo);
		urlDump.put(urlInfo.getOriginUrl(), urlInfo);
		return urlInfo;
	}

	public Url findByShortUrl(String shortUrl) {
		if (ObjectUtils.isEmpty(shortUrlDump.get(shortUrl))) {
			log.info("shortUrl -> {}", shortUrl);
			throw new NotFoundShortUrlException();
		}
		Url url = shortUrlDump.get(shortUrl);
		return shortUrlDump.get(shortUrl);
	}
}
