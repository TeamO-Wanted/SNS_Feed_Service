package com.wanted.sns_feed_service.feed.repository;

import com.wanted.sns_feed_service.feed.dto.StatisticsResponse;
import com.wanted.sns_feed_service.feed.entity.Type;
import com.wanted.sns_feed_service.feed.entity.dto.FeedResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface FeedRepositoryCustom {

    Page<FeedResponseDto> filter(String hashtag,
                                 Type type,
                                 String searchBy,
                                 String order,
                                 String orderTarget,
                                 String searchKeyword,
                                 Pageable pageable);

    List<StatisticsResponse> getFeedStatistics(String hashtag,
                                               String type,
                                               LocalDate start,
                                               LocalDate end,
                                               String value);
}
