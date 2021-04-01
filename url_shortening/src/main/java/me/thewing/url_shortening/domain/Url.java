package me.thewing.url_shortening.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
@EqualsAndHashCode
@AllArgsConstructor
public class Url {
	private final Long id;
	private final String shortUrl;
}
