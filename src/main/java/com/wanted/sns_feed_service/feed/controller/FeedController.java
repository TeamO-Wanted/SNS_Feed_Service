package com.wanted.sns_feed_service.feed.controller;

import com.wanted.sns_feed_service.feed.service.FeedService;
import com.wanted.sns_feed_service.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class FeedController {
    private final FeedService feedService;

    @GetMapping("/feeds")
    public ResponseEntity<CommonResponse> getFeedByHashtag(
            @RequestParam(value = "hashtag", required = false, defaultValue = "기본값") String hashtag,
            @RequestParam(value = "type") String type,
            @RequestParam(value = "start", required = false) LocalDate start,
            @RequestParam(value = "end", required = false) LocalDate end,
            @RequestParam(value = "value", required = false, defaultValue = "count") String value
    ) {
        return ResponseEntity.ok(new CommonResponse("게시글 제목 검색 성공", HttpStatus.OK.value(), feedService.getFeeds(hashtag, type, start, end, value)));
    }
}
