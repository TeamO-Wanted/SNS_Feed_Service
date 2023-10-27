package com.wanted.sns_feed_service.feed.controller;

import com.wanted.sns_feed_service.feed.dto.FeedDetailResponse;
import com.wanted.sns_feed_service.feed.entity.Feed;
import com.wanted.sns_feed_service.feed.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class FeedController {
    private final FeedService feedService;

    // 게시물 상세 조회
    @GetMapping("/feeds/{id}")
    public ResponseEntity<FeedDetailResponse> findFeed(@PathVariable long id) {
        Feed feed = feedService.findById(id);

        return ResponseEntity.ok().body(new FeedDetailResponse(feed));
    }
}
