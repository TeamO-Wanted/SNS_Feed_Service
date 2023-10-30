package com.wanted.sns_feed_service.feed.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.wanted.sns_feed_service.hashTag.entity.HashTag;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
	@Column(unique = true)
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

	@ManyToMany
	@Builder.Default
	@ToString.Exclude
	private List<HashTag> hashTags = new ArrayList<>();

	@PrePersist
	public void prePersist() {
		this.updatedAt = null;
	}

	public void addTag(HashTag hashTag) {
		this.hashTags.add(hashTag);
	}

	public void updateViewCount() {
		this.viewCount++;
	}

	public void updateLikeCount() {
		this.likeCount++;
	}

	public void updateShareCount() {
		this.shareCount++;
	}
}
