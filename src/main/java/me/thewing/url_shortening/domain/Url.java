package me.thewing.url_shortening.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import me.thewing.url_shortening.dto.IpDto;

@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
@AllArgsConstructor
public class Url {
	private final String shortUrl;
	private final String originUrl;
	private final int requestCount;
	private final List<IpDto> ip;
}
