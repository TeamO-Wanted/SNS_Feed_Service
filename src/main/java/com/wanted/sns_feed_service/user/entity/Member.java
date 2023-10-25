package com.wanted.sns_feed_service.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Member {
  @Id
  @GeneratedValue
  private Long id;
  private String account;
  private String password;
  private String email;
  private String accessToken;
}
