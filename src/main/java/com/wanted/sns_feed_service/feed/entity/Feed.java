package com.wanted.sns_feed_service.feed.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder(toBuilder = true)
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
public class Feed {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String contentId;
  @Enumerated(EnumType.STRING)
  private Type type;
  private String title;
  private String content;
  private int viewCount;
  private int likeCount;
  private int shareCount;
  @LastModifiedDate
  private LocalDateTime updatedAt;
  @CreatedDate
  private LocalDateTime createdAt;
  @PrePersist
  public void prePersist() {
    this.updatedAt = null;
  }
}
