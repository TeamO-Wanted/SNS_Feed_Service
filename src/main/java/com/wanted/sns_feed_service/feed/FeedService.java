package com.wanted.sns_feed_service.feed;

import com.wanted.sns_feed_service.feed.entity.Type;
import com.wanted.sns_feed_service.feed.entity.dto.FeedResponseDto;
import com.wanted.sns_feed_service.feed.repository.FeedRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class FeedService {

    private final FeedRepository feedRepository;

    /**
     * 검색 필터링
     */
    public Page<FeedResponseDto> search(String hashtag, Type type, String searchBy, String orderBy, String orderTarget, String searchKeyword, Pageable pageable) {
        // TODO hashtag      미 입력시 본인 계정

        return feedRepository.filter(hashtag, type, searchBy, orderBy, orderTarget, searchKeyword, pageable);
    }
}
