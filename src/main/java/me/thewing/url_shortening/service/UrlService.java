package me.thewing.url_shortening.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.thewing.url_shortening.common.utils.Sha256;
import me.thewing.url_shortening.domain.Url;
import me.thewing.url_shortening.dto.IpDto;
import me.thewing.url_shortening.exception.NotFoundShortUrlException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UrlService {

	private final HashMap<String, Url> urlDump;
	private final HashMap<String, Url> shortUrlDump;

	public Url save(String originUrl, HttpServletRequest request) {
		if (isExistByOriginUrl(originUrl)) {
			return urlDump.get(originUrl);
		} else {
			return store(originUrl, request);
		}
	}

	public boolean isExistByOriginUrl(String url) {
		return urlDump.containsKey(url);
	}
	public boolean isExistByShortUrl(String url) {
		return shortUrlDump.containsKey(url);
	}
	private Url store(String url, HttpServletRequest request) {
		int pos = 0;
		String shortUrl = Sha256.encode(url, pos);
		while (isExistByShortUrl(shortUrl)) {
			shortUrl = Sha256.encode(url, ++pos);
		}
		IpDto ipInfo = IpDto.builder()
				.ip(getIp(request))
				.accessDate(LocalDateTime.now())
				.build();
		List<IpDto> ips = new ArrayList<>();
		ips.add(ipInfo);
		Url urlInfo = Url.builder()
				.shortUrl(shortUrl)
				.originUrl(url)
				.requestCount(0)
				.ip(ips)
				.build();

		shortUrlDump.put(urlInfo.getShortUrl(), urlInfo);
		urlDump.put(urlInfo.getOriginUrl(), urlInfo);
		return urlInfo;
	}

	public Url findByShortUrl(String shortUrl, HttpServletRequest request) {
		System.out.println("shortUrlDump = " + shortUrlDump);
		System.out.println("shortUrlDump.get(\"shortUrl\") = " + shortUrlDump.get(shortUrl));
		if (ObjectUtils.isEmpty(shortUrlDump.get(shortUrl))) {
			log.info("shortUrl -> {}", shortUrl);
			throw new NotFoundShortUrlException();
		}
		Url url = shortUrlDump.get(shortUrl);
		String ip = getIp(request);
		List<IpDto> ipInfo = url.getIp();
		LocalDateTime saveTime = LocalDateTime.now();
		long oneDay = Duration.ofDays(1).getSeconds();
		for (IpDto ipDto : ipInfo) {
			if (ipDto.getIp().equals(ip) && Duration.between(saveTime, ipDto.getAccessDate()).getSeconds() < oneDay) {
				return shortUrlDump.get(shortUrl);
			}
		}
		ipInfo.add(new IpDto(ip, saveTime));
		Url urlInfo = Url.builder()
				.shortUrl(url.getShortUrl())
				.originUrl(url.getOriginUrl())
				.requestCount(url.getRequestCount() + 1)
				.ip(ipInfo)
				.build();
		shortUrlDump.put(urlInfo.getShortUrl(), urlInfo);
		urlDump.put(urlInfo.getOriginUrl(), urlInfo);
		return shortUrlDump.get(shortUrl);
	}

	private String getIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-RealIP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("REMOTE_ADDR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public Url getLogs(String shortUrl) {
		return shortUrlDump.get(shortUrl);
	}
}
