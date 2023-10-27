package com.wanted.sns_feed_service.hashTag.entity;

import java.util.ArrayList;
import java.util.List;

import com.wanted.sns_feed_service.feed.entity.Feed;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class HashTag {
	@Id
	private String name;

	@ManyToMany
	@Builder.Default
	@ToString.Exclude
	private List<Feed> feeds = new ArrayList<>();
}
