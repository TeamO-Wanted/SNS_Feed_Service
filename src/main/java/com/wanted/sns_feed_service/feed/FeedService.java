package com.wanted.sns_feed_service.feed;

import com.wanted.sns_feed_service.feed.entity.Feed;
import com.wanted.sns_feed_service.feed.entity.Type;
import com.wanted.sns_feed_service.feed.entity.dto.FeedResponseDto;
import com.wanted.sns_feed_service.feed.repository.FeedRepository;
import com.wanted.sns_feed_service.response.CommonResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

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
    public Feed likeById(long id) throws Exception {
        Feed feed = feedRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found: " + id));
        // 외부 SNS API 호출 로직: 구현은 했으나 실제 유효한 URL이 아니므로 주석 처리. 필요시 주석 해제.
//        webClientService.sendSnsApi("/likes", feed);
        feed.updateLikeCount();
        return feed;
    }

    /**
     * 피드 공유
     */
    @Transactional
    public Feed shareById(long id) throws Exception {
        Feed feed = feedRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found: " + id));
        // 외부 SNS API 호출 로직: 구현은 했으나 실제 유효한 URL이 아니므로 주석 처리. 필요시 주석 해제.
//        webClientService.sendSnsApi("/share", feed);
        feed.updateShareCount();
        return feed;
    }

    public CommonResponse getFeeds(String hashtag, String type, LocalDate start, LocalDate end, String value) {
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
            return new CommonResponse("통계 자료 조회 실패: 날짜별 조회는 30일 이하만 가능합니다.", HttpStatus.BAD_REQUEST.value());

        if (type.equals("hour") && diff > 86400 * 7)
            return new CommonResponse("통계 자료 조회 실패: 날짜/시간 별 조회는 7일 이하만 가능합니다.", HttpStatus.BAD_REQUEST.value());
        return new CommonResponse("통계 자료 조회 성공", HttpStatus.OK.value(), feedRepository.getFeedStatistics(hashtag, type, start, end, value));

    }
}
