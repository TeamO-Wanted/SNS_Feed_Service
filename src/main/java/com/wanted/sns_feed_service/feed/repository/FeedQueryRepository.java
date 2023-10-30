package com.wanted.sns_feed_service.feed.repository;

import com.wanted.sns_feed_service.feed.dto.StatisticsResponse;

import java.time.LocalDate;
import java.util.List;

public interface FeedQueryRepository {
    List<StatisticsResponse> getFeedStatistics(String hashtag, String type, LocalDate start, LocalDate end, String value);
}