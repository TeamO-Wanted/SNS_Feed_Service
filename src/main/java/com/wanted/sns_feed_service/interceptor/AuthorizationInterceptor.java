package com.wanted.sns_feed_service.interceptor;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.wanted.sns_feed_service.exceptionHandler.AuthenticationException;
import com.wanted.sns_feed_service.jwt.JwtProvider;
import com.wanted.sns_feed_service.member.entity.Member;
import com.wanted.sns_feed_service.member.repository.MemberRepository;
import com.wanted.sns_feed_service.resolver.LoginMemberContext;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthorizationInterceptor implements HandlerInterceptor {
	private final JwtProvider jwtProvider;
	private final MemberRepository memberRepository;
	private final LoginMemberContext loginMemberContext;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
		Exception {
		String token = extractToken(request);
		if (token == null)
			throw new AuthenticationException("JWT를 요청 헤더에 넣어주세요");
		token = token.substring("Bearer ".length());
		if (!jwtProvider.verify(token))
			throw new AuthenticationException("유효하지 않은 토큰입니다.");
		Optional<Member> memberOptional = extractMember(token);
		if (memberOptional.isEmpty())
			throw new AuthenticationException("존재하지 않는 사용자입니다.");
		if (!verifyAccessToken(token, memberOptional))
			throw new AuthenticationException("저장된 토큰 정보가 유효하지 않습니다.");
		//사용자 정보 context 등록
		registerLoginMemberContext(memberOptional);
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
		Exception ex) throws Exception {
		releaseLoginMemberContext();
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}

	private String extractToken(HttpServletRequest request) {
		return request.getHeader("Authorization");
	}

	private Optional<Member> extractMember(String token) {
		return memberRepository.findById(Long.valueOf((Integer)jwtProvider.getClaims(token).get("id")));
	}

	private boolean verifyAccessToken(String token, Optional<Member> memberOptional) {
		if (memberOptional.isEmpty())
			throw new AuthenticationException("존재하지 않는 사용자입니다.");
		return token.equals(memberOptional.get().getAccessToken());
	}

	private void registerLoginMemberContext(Optional<Member> memberOptional) {
		if (memberOptional.isEmpty())
			return;
		loginMemberContext.save(memberOptional.get());
	}

	private void releaseLoginMemberContext() {
		loginMemberContext.remove();
	}
}
