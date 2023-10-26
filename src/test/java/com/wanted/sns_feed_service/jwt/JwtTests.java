package com.wanted.sns_feed_service.jwt;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

import java.util.Base64;

import javax.crypto.SecretKey;

import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class JwtTests {
	@Value("${custom.jwt.secretKey}")
	private String secretKeyPlain;

	@Autowired
	private JwtProvider jwtProvider;

	@Test
	@DisplayName("secretKey 키가 존재해야한다.")
	void t1() {
		assertThat(secretKeyPlain).isNotNull();
	}

	@Test
	@DisplayName("sercretKey 원문으로 hmac 암호화 알고리즘에 맞는 SecretKey 객체를 만들 수 있다.")
	void t2() {
		// 키를 Base64 인코딩 한다.
		String keyBase64Encoded = Base64.getEncoder().encodeToString(secretKeyPlain.getBytes());
		// Base64 인코딩된 키를 이용하여 SecretKey 객체를 만든다.
		SecretKey secretKey = Keys.hmacShaKeyFor(keyBase64Encoded.getBytes());

		assertThat(secretKey).isNotNull();
	}

	@Test
	@DisplayName("JwtProvider 객체로 SecretKey 객체를 생성할 수 있다.")
	void t3() {
		SecretKey secretKey = jwtProvider.getSecretKey();

		assertThat(secretKey).isNotNull();
	}

	@Test
	@DisplayName("SecretKey 객체는 단 한번만 생성되어야 한다.")
	void t4() {
		SecretKey secretKey1 = jwtProvider.getSecretKey();
		SecretKey secretKey2 = jwtProvider.getSecretKey();

		assertThat(secretKey1 == secretKey2).isTrue();
	}
}