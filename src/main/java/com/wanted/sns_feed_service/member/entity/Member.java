package com.wanted.sns_feed_service.member.entity;

import static jakarta.persistence.GenerationType.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Member {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;
	@Column(unique = true)
	private String account;
	@JsonIgnore
	private String password;
	@Email(message = "올바른 이메일 형식을 입력하세요")
	private String email;
	private String accessToken;
}
