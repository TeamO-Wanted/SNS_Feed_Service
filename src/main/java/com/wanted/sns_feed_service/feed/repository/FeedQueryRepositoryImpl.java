package com.wanted.sns_feed_service.feed.repository;

import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wanted.sns_feed_service.feed.dto.StatisticsResponse;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.wanted.sns_feed_service.feed.entity.QFeed.feed;

@RequiredArgsConstructor
public class FeedQueryRepositoryImpl implements FeedQueryRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<StatisticsResponse> getFeedStatistics(String hashtag, String type, LocalDate start, LocalDate end, String value) {
        //날짜/시간 형식 설정
        StringTemplate formattedDate = getFormattedDate(feed.createdAt, type);
        //count 일 시 게시물 개수 일자별 출력, 아닐 때는 view count 등 합계 출력
        return queryFactory
                .select(Projections.fields(StatisticsResponse.class, formattedDate.as("date"), value.equals("count") ? feed.count().intValue().as("count") : value.equals("view_count") ? feed.viewCount.sum().as("count") : value.equals("like_count") ? feed.likeCount.sum().as("count") : feed.shareCount.sum().as("count")))
                .from(feed)
                .where(feed.hashTags.any().name.eq(hashtag),
                        feed.createdAt.between(start.atStartOfDay(), end.plusDays(1).atStartOfDay().minusNanos(1))
                ).groupBy(formattedDate).fetch();
    }

    protected StringTemplate getFormattedDate(DateTimePath<LocalDateTime> date, String type) {
        return Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})",
                date,
                ConstantImpl.create(type.equals("date") ? "%Y-%m-%d" : "%Y-%m-%d %H:00"));
    }
}

