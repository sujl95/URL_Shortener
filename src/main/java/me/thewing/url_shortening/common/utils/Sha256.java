package me.thewing.url_shortening.common.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Sha256 {
	public static String encode(String str, int pos) {
		MessageDigest messageDigest;
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(str.getBytes(StandardCharsets.UTF_8));
		} catch (NoSuchAlgorithmException exception) {
			throw new IllegalArgumentException(exception.getMessage());
		}
		Base64.Encoder encoder = Base64.getEncoder();
		return encoder.encodeToString(encoder.encode(messageDigest.digest())).substring(pos, 8 + pos);
	}

}
