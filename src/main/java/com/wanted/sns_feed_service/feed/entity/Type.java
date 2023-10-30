package com.wanted.sns_feed_service.feed.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Type {
  FACEBOOK("https://www.facebook.com"),
  TWITTER("https://www.twitter.com"),
  INSTAGRAM("https://www.instagram.com"),
  THREADS("https://www.threads.net");

  private final String url;
}