package com.wanted.sns_feed_service.member.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
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

	private final JavaMailSender mailSender;
	@Value("${spring.mail.username}")
	private String ADMIN_ADDRESS;

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
		// 메일 발송
		sendEmail(email, account, Integer.toString(temp));

		return RsData.of("S-1", "회원가입 완료 최초 로그인 시 이메일 인증 코드를 확인하고 입력해주세요");
	}

	@Async // 비동기
	public void sendEmail(String email, String userName, String tempCode) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email);
		message.setFrom(ADMIN_ADDRESS);
		message.setSubject(userName+"님의 임시 인증코드 안내 메일입니다.");
		message.setText("안녕하세요 "+userName+"님의 임시 인증 코드는 [" + tempCode +"] 입니다. \n 최초 로그인 시 인증 코드를 입력해주세요.");
		mailSender.send(message);
	}

	public int createNumber(){
		return (int)(Math.random() * (90000)) + 100000;// (int) Math.random() * (최댓값-최소값+1) + 최소값
	}

}
