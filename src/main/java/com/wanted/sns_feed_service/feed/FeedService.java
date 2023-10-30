package com.wanted.sns_feed_service.feed;

import com.wanted.sns_feed_service.feed.entity.Feed;
import com.wanted.sns_feed_service.feed.entity.Type;
import com.wanted.sns_feed_service.feed.entity.dto.FeedResponseDto;
import com.wanted.sns_feed_service.feed.repository.FeedRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Transactional
public class FeedService {

    private final FeedRepository feedRepository;
    private final WebClientService webClientService;

    /**
     * 검색 필터링
     */
    public Page<FeedResponseDto> search(String hashtag, Type type, String searchBy, String orderBy, String orderTarget, String searchKeyword, Pageable pageable) {
        // TODO hashtag      미 입력시 본인 계정

        return feedRepository.filter(hashtag, type, searchBy, orderBy, orderTarget, searchKeyword, pageable);
    }

    /**
     * 피드 상세 조회
     */
    @Transactional
    public Feed findById(long id) {
        Feed feed = feedRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        feed.updateViewCount();

        return feed;
    }

    /**
     * 피드 좋아요
     */
    @Transactional
    public Feed likeById(long id) {
        Feed feed = feedRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found: " + id));
        webClientService.sendSnsApi("/likes", feed);
        feed.updateLikeCount();
        return feed;
    }

    /**
     * 피드 공유
     */
    @Transactional
    public Feed shareById(long id) {
        Feed feed = feedRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found: " + id));
        webClientService.sendSnsApi("/share", feed);
        feed.updateShareCount();
        return feed;
    }
}
