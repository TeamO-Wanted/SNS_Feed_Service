package com.wanted.sns_feed_service.feed.entity.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wanted.sns_feed_service.feed.entity.Type;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FeedRequestDto {

    private Long id;
    private String contentId;
    private Type type;
    private String title;
    private String content;
    private int viewCount;
    private int likeCount;
    private int shareCount;
}
