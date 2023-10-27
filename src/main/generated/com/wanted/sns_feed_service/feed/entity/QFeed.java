package com.wanted.sns_feed_service.feed.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFeed is a Querydsl query type for Feed
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFeed extends EntityPathBase<Feed> {

    private static final long serialVersionUID = 902296570L;

    public static final QFeed feed = new QFeed("feed");

    public final StringPath content = createString("content");

    public final StringPath contentId = createString("contentId");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final ListPath<com.wanted.sns_feed_service.hashTag.entity.HashTag, com.wanted.sns_feed_service.hashTag.entity.QHashTag> hashTags = this.<com.wanted.sns_feed_service.hashTag.entity.HashTag, com.wanted.sns_feed_service.hashTag.entity.QHashTag>createList("hashTags", com.wanted.sns_feed_service.hashTag.entity.HashTag.class, com.wanted.sns_feed_service.hashTag.entity.QHashTag.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> likeCount = createNumber("likeCount", Integer.class);

    public final NumberPath<Integer> shareCount = createNumber("shareCount", Integer.class);

    public final StringPath title = createString("title");

    public final EnumPath<Type> type = createEnum("type", Type.class);

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public final NumberPath<Integer> viewCount = createNumber("viewCount", Integer.class);

    public QFeed(String variable) {
        super(Feed.class, forVariable(variable));
    }

    public QFeed(Path<? extends Feed> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFeed(PathMetadata metadata) {
        super(Feed.class, metadata);
    }

}

