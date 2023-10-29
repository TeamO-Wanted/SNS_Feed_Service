package com.wanted.sns_feed_service.member.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class MemberControllerTests {
	@Autowired
	private MockMvc mvc;

	@Test
	@DisplayName("POST /member/signup 은 회원가입을 처리한다.")
	void t1() throws Exception {
		// When
		ResultActions resultActions = mvc
			.perform(
				post("/member/signup")
					.content("""
						{
						    "account": "puar12",
						    "password": "cjfgus2514",
						    "email": "aaaaa@naver.com"
						}
						""".stripIndent())
					// JSON 형태로 보내겠다
					.contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
			)
			.andDo(print());

		// Then
		resultActions
			.andExpect(status().is2xxSuccessful())
			.andExpect(jsonPath("$.resultCode").value("S-1"))
			.andExpect(jsonPath("$.msg").value("회원가입 완료 최초 로그인 시 이메일 인증 코드를 확인하고 입력해주세요"));
	}

	@Test
	@DisplayName("회원가입 시 이메일을 입력하지 않으면 가입이 되지 않는다.")
	void t2() throws Exception {
		// When
		ResultActions resultActions = mvc
			.perform(
				post("/member/signup")
					.content("""
						{
						    "account": "puar12",
						    "password": "cjfgus2514",
						    "email": ""
						}
						""".stripIndent())
					// JSON 형태로 보내겠다
					.contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
			)
			.andDo(print());

		// Then
		resultActions
			.andExpect(status().is2xxSuccessful())
			.andExpect(jsonPath("$.resultCode").value("F-1"))
			.andExpect(jsonPath("$.msg").value("이메일을 입력해주세요"));
	}

	@Test
	@DisplayName("회원가입 시 비밀번호 제약조건을 지키지 않으면 가입이 되지 않는다.")
	void t3() throws Exception {
		// When
		ResultActions resultActions = mvc
			.perform(
				post("/member/signup")
					.content("""
						{
						    "account": "puar12",
						    "password": "1234",
						    "email": "aaaaa@naver.com"
						}
						""".stripIndent())
					// JSON 형태로 보내겠다
					.contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
			)
			.andDo(print());

		// Then
		resultActions
			.andExpect(status().is2xxSuccessful())
			.andExpect(jsonPath("$.resultCode").value("F-1"))
			.andExpect(jsonPath("$.msg").value("비밀번호는 10자 이상 입력해야 하며, 숫자로만 이루어 질 수 없으며 3회 이상 연속되는 문자를 사용할 수 없습니다."));
	}
}
