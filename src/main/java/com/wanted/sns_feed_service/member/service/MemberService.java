package com.wanted.sns_feed_service.member.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanted.sns_feed_service.jwt.JwtProvider;
import com.wanted.sns_feed_service.member.entity.Member;
import com.wanted.sns_feed_service.member.repository.MemberRepository;
import com.wanted.sns_feed_service.rsData.RsData;
import com.wanted.sns_feed_service.util.Ut;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
	private final MemberRepository memberRepository;
	private final JwtProvider jwtProvider;

	@Transactional
	public RsData join(String account, String password, String email) {
		Optional<Member> opMember = memberRepository.findByAccount(account);
		if(opMember.isPresent()) {
			return RsData.of("F-1", "이미 가입한 Id가 있습니다.");
		}

		Member member = Member.builder()
			.account(account)
			.password(Ut.encrypt.encryptPW(password))
			.email(email)
			.build();

		memberRepository.save(member);

		return RsData.of("S-1", "회원가입 완료, 로그인 후 이용해주세요!");
	}

}
