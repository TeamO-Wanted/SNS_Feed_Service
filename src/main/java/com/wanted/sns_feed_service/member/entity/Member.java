package com.wanted.sns_feed_service.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
  @GeneratedValue
  private Long id;
  @Column(unique = true)
  private String account;
  private String password;
  private String email;
  private String accessToken;
}
