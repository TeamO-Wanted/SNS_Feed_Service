package com.wanted.sns_feed_service.email.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmailService {
	@Value("${spring.mail.username}")
	private String ADMIN_ADDRESS;
	private final JavaMailSender mailSender;

	@Async // 비동기
	public void sendEmail(String email, String userName, String tempCode) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email);
		message.setFrom(ADMIN_ADDRESS);
		message.setSubject(userName+"님의 임시 인증코드 안내 메일입니다.");
		message.setText("안녕하세요 "+userName+"님의 임시 인증 코드는 [" + tempCode +"] 입니다. \n 최초 로그인 시 인증 코드를 입력해주세요.");
		mailSender.send(message);
	}
}