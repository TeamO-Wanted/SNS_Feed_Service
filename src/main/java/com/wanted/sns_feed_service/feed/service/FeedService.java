package com.wanted.sns_feed_service.feed.service;

import com.wanted.sns_feed_service.feed.dto.StatisticsResponse;
import com.wanted.sns_feed_service.feed.repository.FeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedService {
    private final FeedRepository feedRepository;

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
