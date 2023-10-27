package com.wanted.sns_feed_service.feed.service;

import com.wanted.sns_feed_service.feed.entity.Feed;
import com.wanted.sns_feed_service.feed.repository.FeedRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FeedService {
    private final FeedRepository feedRepository;

    // 게시물 상세 조회
    @Transactional
    public Feed findById(long id) {
        Feed feed = feedRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        feed.updateViewCount(feed.getViewCount() + 1);

        return feed;
    }
}
