package com.wanted.sns_feed_service.feed;

import com.wanted.sns_feed_service.feed.dto.StatisticsResponse;
import com.wanted.sns_feed_service.feed.entity.Feed;
import com.wanted.sns_feed_service.feed.entity.Type;
import com.wanted.sns_feed_service.feed.entity.dto.FeedResponseDto;
import com.wanted.sns_feed_service.feed.repository.FeedRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

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

    /**
     * 피드 상세 조회
     */
    @Transactional
    public Feed findById(long id) {
        Feed feed = feedRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        feed.updateViewCount();

        return feed;
    }

    public List<StatisticsResponse> getFeeds(String hashtag, String type, LocalDate start, LocalDate end, String value) {
        //TODO jwt, 로그인 적용 후 로그인 된 유저(토큰정보 추출)해서 입력값없을 떄 본인 hashtag 검색 되도록
        if (start == null) {
            start = LocalDate.parse(LocalDate.now().minusDays(7).format(DateTimeFormatter.ofPattern(type.equals("date") ? "yyyy-MM-dd" : "yyyy-MM-dd hh:00")));
        }
        if (end == null) {
            end = LocalDate.parse(LocalDate.now().format(DateTimeFormatter.ofPattern(type.equals("date") ? "yyyy-MM-dd" : "yyyy-MM-dd hh:00")));
        }
        long diff = ChronoUnit.SECONDS.between(start.atStartOfDay(), end.plusDays(1).atStartOfDay().minusSeconds(1));

        //날짜 기간이 30일, 7일에 해당하는지 확인
        if (type.equals("date") && (diff > 86400 * 30))
            throw new IllegalArgumentException("날짜별 검색 30일 이하만 가능합니다.");
        if (type.equals("hour") && diff > 86400 * 7)
            throw new IllegalArgumentException("날짜/시간 별 검색은 7일 이하만 가능합니다.");
        return feedRepository.getFeedStatistics(hashtag, type, start, end, value);
    }
}
