package com.wanted.sns_feed_service.jwt;
import java.util.Base64;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {
	private SecretKey cachedSecretKey;

	@Value("${custom.jwt.secretKey}")
	private String secretKeyPlain;

	private SecretKey _getSecretKey() {
		// 시크릿 키 객체 생성 : 시크릿 키 평문 Base64 인코딩 -> hmac 인코딩
		String keyBase64Encoded = Base64.getEncoder().encodeToString(secretKeyPlain.getBytes());
		return Keys.hmacShaKeyFor(keyBase64Encoded.getBytes());
	}

	// 시크릿키 객체가 있으면 저장된거 사용, 없으면 생성
	public SecretKey getSecretKey() {
		if (cachedSecretKey == null) cachedSecretKey = _getSecretKey();

		return cachedSecretKey;
	}
}