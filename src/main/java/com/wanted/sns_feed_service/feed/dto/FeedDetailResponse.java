package com.wanted.sns_feed_service.feed.dto;

import com.wanted.sns_feed_service.feed.entity.Feed;
import com.wanted.sns_feed_service.hashTag.entity.HashTag;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class FeedDetailResponse {
    private final String content_id;
    private final String type;
    private final String title;
    private final String content;
    private final List<HashTag> hashtags;
    private final int viewCount;
    private final int likeCount;
    private final int shareCount;
    private final LocalDateTime updatedAt;
    private final LocalDateTime createdAt;

    public FeedDetailResponse(Feed feed) {
        this.content_id = feed.getContentId();
        this.type = feed.getType().name();
        this.title = feed.getTitle();
        this.content = feed.getContent();
        this.hashtags = feed.getHashTags();
        this.viewCount = feed.getViewCount();
        this.likeCount = feed.getLikeCount();
        this.shareCount = feed.getShareCount();
        this.updatedAt = feed.getUpdatedAt();
        this.createdAt = feed.getCreatedAt();
    }
}
