package com.wanted.sns_feed_service.feed.repository;

import com.wanted.sns_feed_service.feed.entity.Type;
import com.wanted.sns_feed_service.feed.entity.dto.FeedResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FeedRepositoryCustom {

    Page<FeedResponseDto> filter(String hashtag,
                                 Type type,
                                 String searchBy,
                                 String order,
                                 String orderTarget,
                                 String searchKeyword,
                                 Pageable pageable);
}
