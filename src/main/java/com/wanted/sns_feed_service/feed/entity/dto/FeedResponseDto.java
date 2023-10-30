package com.wanted.sns_feed_service.feed.entity.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wanted.sns_feed_service.feed.entity.Feed;
import com.wanted.sns_feed_service.feed.entity.Type;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FeedResponseDto {

    private Long id;
    private String contentId;
    private Type type;
    private String title;
    private String content;
    private int viewCount;
    private int likeCount;
    private int shareCount;

    public static FeedResponseDto of(Feed feed) {
        return FeedResponseDto.builder()
                .contentId(feed.getContentId())
                .type(feed.getType())
                .title(feed.getTitle())
                .content(feed.getContent())
                .viewCount(feed.getViewCount())
                .likeCount(feed.getLikeCount())
                .shareCount(feed.getShareCount())
                .build();
    }

    public static List<FeedResponseDto> of(List<Feed> feedList) {

        List<FeedResponseDto> feedResponseDtoList = new ArrayList<>();

        for (Feed feed : feedList) {
            String content = feed.getContent();
            String newContent = content.length() > 20 ? feed.getContent().substring(0, 20) + "..." : content;

            feedResponseDtoList.add(FeedResponseDto.builder()
                    .contentId(feed.getContentId())
                    .type(feed.getType())
                    .title(feed.getTitle())
                    .content(newContent)
                    .viewCount(feed.getViewCount())
                    .likeCount(feed.getLikeCount())
                    .shareCount(feed.getShareCount())
                    .build()
            );
        }
        return feedResponseDtoList;
    }
}
