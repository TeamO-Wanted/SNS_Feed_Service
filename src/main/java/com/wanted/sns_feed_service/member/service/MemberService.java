package com.wanted.sns_feed_service.member.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanted.sns_feed_service.email.service.EmailService;
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

	private final EmailService emailService;


	@Transactional
	public RsData join(String account, String password, String email) {
		Optional<Member> opMember = memberRepository.findByAccount(account);
		if(opMember.isPresent()) {
			return RsData.of("F-1", "이미 가입한 Id가 있습니다.");
		}

		// 인증코드 6자리 발급 및 저장
		int temp = createNumber();

		Member member = Member.builder()
			.account(account)
			.password(Ut.encrypt.encryptPW(password))
			.email(email)
			.tempCode(temp)
			.build();

		memberRepository.save(member);

		// 비동기 방식의 메서드를 다른 class에서 호출해야 하기에 따로 호출
		emailService.sendEmail(email, account, Integer.toString(temp));

		return RsData.of("S-1", "회원가입 완료 최초 로그인 시 이메일 인증 코드를 확인하고 입력해주세요");
	}

	public int createNumber(){
		return (int)(Math.random() * (90000)) + 100000;// (int) Math.random() * (최댓값-최소값+1) + 최소값
	}

}
