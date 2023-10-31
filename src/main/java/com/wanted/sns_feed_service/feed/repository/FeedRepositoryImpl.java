package com.wanted.sns_feed_service.feed.repository;

import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQuery;
import com.wanted.sns_feed_service.feed.dto.StatisticsResponse;
import com.wanted.sns_feed_service.feed.entity.Feed;
import com.wanted.sns_feed_service.feed.entity.Type;
import com.wanted.sns_feed_service.feed.entity.dto.FeedResponseDto;
import com.wanted.sns_feed_service.feed.repository.support.Querydsl4RepositorySupport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.wanted.sns_feed_service.feed.entity.QFeed.feed;

public class FeedRepositoryImpl extends Querydsl4RepositorySupport implements FeedRepositoryCustom {
    public FeedRepositoryImpl() {
        super(Feed.class);
    }

    /**
     * 검색 필터
     */
    @Override
    public Page<FeedResponseDto> filter(String hashtag, // hashtag
                                        Type type, // feed type
                                        String searchBy, // 검색 기준 (제목, 내용, 제목 + 내용)
                                        String orderBy,// 정렬 순서 (DESC, ASC)
                                        String orderTarget,// 정렬 기준 (등록 일시, count 순서 등 )
                                        String searchKeyword,// 검색어
                                        Pageable pageable) { // 페이지 정보

        JPAQuery<Feed> feedJPAQuery = selectFrom(feed)
                .where(
                        hashtagFilter(hashtag),
                        typeFilter(type),
                        searchFilter(searchBy, searchKeyword)
                );

        List<FeedResponseDto> feedList = FeedResponseDto.of(feedJPAQuery
                .orderBy(orderByFilter(Order.valueOf(orderBy.toUpperCase()), orderTarget))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch());

        // for data count
        long count = feedJPAQuery.fetchCount();

        return new PageImpl<>(feedList, pageable, count);

    }

    /**
     * 키워드 검색
     */
    private BooleanExpression searchFilter(String searchBy, String searchKeyword) {

        if (searchKeyword == null || searchKeyword.isEmpty()) {
            return null; // 검색 키워드가 없을 때 필터링하지 않음
        }

        if (searchBy == null || searchBy.equals("title_content")) {
            // searchBy가 null인 경우, 디폴트로 타이틀 + 내용 전체 검색
            return feed.title.containsIgnoreCase(searchKeyword).or(feed.content.containsIgnoreCase(searchKeyword));
        }

        switch (searchBy) {
            case "title": // 타이틀 검색
                return feed.title.containsIgnoreCase(searchKeyword);
            case "content": // 내용 검색
                return feed.content.containsIgnoreCase(searchKeyword);
        }
        // 이외의 경우는 타이틀 + 내용 전체 검색
        return feed.title.containsIgnoreCase(searchKeyword).or(feed.content.containsIgnoreCase(searchKeyword));
    }

    /**
     * 정렬
     * - created_at,updated_at,like_count,share_count,view_count 사용 가능
     * - 오름차순 , 내림차순 가능
     */
    private OrderSpecifier<?> orderByFilter(Order orderBy, String orderTarget) {

        switch (orderTarget) {
            case "created_at":
                return new OrderSpecifier<>(orderBy, feed.createdAt);
            case "updated_at":
                return new OrderSpecifier<>(orderBy, feed.updatedAt);
            case "like_count":
                return new OrderSpecifier<>(orderBy, feed.likeCount);
            case "share_count":
                return new OrderSpecifier<>(orderBy, feed.shareCount);
            case "view_count":
                return new OrderSpecifier<>(orderBy, feed.viewCount);
        }
        return new OrderSpecifier<>(orderBy, feed.createdAt); // 정렬 미입력 시 최신 생성 순
    }


    /**
     * hashtag 조회
     */
    private BooleanExpression hashtagFilter(String hashtag) {
        if (hashtag == null || hashtag.isEmpty()) {
            return feed.hashTags.any().isNotNull(); // TODO : null 이면 자신의 계정 반환하게 하기, 현재는 모든 목록 불러오게 함.
        }
        return feed.hashTags.any().name.eq(hashtag);
    }

    /**
     * type 조회
     */
    private BooleanExpression typeFilter(Type type) {
        return type != null ? feed.type.eq(type) : null;
    }


    /**
     * 통계
     */
    @Override
    public List<StatisticsResponse> getFeedStatistics(String hashtag, String type, LocalDate start, LocalDate end, String value) {
        //날짜/시간 형식 설정
        StringTemplate formattedDate = getFormattedDate(feed.createdAt, type);
        //count 일 시 게시물 개수 일자별 출력, 아닐 때는 view count 등 합계 출력
        return select(Projections.fields(StatisticsResponse.class, formattedDate.as("date"), value.equals("count") ? feed.count().as("count") : value.equals("view_count") ? feed.viewCount.sum().longValue().as("count") : value.equals("like_count") ? feed.likeCount.sum().longValue().as("count") : feed.shareCount.sum().longValue().as("count")))
                .from(feed)
                .where(hashtagFilter(hashtag),
                        feed.createdAt.between(start.atStartOfDay(), end.plusDays(1).atStartOfDay().minusNanos(1))
                ).groupBy(formattedDate).fetch();
    }

    private StringTemplate getFormattedDate(DateTimePath<LocalDateTime> date, String type) {
        return Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})",
                date,
                ConstantImpl.create(type.equals("date") ? "%Y-%m-%d" : "%Y-%m-%d %H:00"));
    }
}
