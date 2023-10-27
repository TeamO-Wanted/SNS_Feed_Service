package com.wanted.sns_feed_service.member.controller;

import static org.springframework.http.MediaType.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wanted.sns_feed_service.member.service.MemberService;
import com.wanted.sns_feed_service.rsData.RsData;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/member", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
@Tag(name = "MemberController", description = "회원가입, 로그인처리 컨트롤러")
public class MemberController {
	private final MemberService memberService;

	@Data
	public static class SignupRequest {
		@NotBlank(message = "id를 입력해주세요")
		private String account;
		@NotBlank(message = "pw를 입력해주세요")
		@Pattern(regexp = "^(?![0-9]{10,}$)(?!.*(.)\\1{2,}).{10,}$", message = "비밀번호는 10자 이상 입력해야 하며, 숫자로만 이루어 질 수 없으며 3회 이상 연속되는 문자를 사용할 수 없습니다.")
		private String password;
		@Email(message = "올바른 이메일 형식을 입력하세요")
		@NotBlank(message = "이메일을 입력해주세요")
		private String email;
	}

	@PostMapping("/signup")
	@Operation(summary = "회원가입")
	public RsData signup(@Valid @RequestBody SignupRequest signupRequest, BindingResult bindingResult) {
		// 요청 객체에서 입력하지 않은 부분이 있다면 메세지를 담아서 RsData 객체 바로 리턴
		if (bindingResult.hasErrors()) {
			List<String> errorMessages = bindingResult.getAllErrors()
				.stream()
				.map(error -> error.getDefaultMessage())
				.collect(Collectors.toList());
			return RsData.of("F-1", errorMessages.get(0));
		}
		// 입력 이상 없다면 계정 가입
		RsData rsData = memberService.join(signupRequest.getAccount(), signupRequest.getPassword(), signupRequest.getEmail());

		return rsData;
	}

}
