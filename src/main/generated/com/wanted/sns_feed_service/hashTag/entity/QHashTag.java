package com.wanted.sns_feed_service.hashTag.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHashTag is a Querydsl query type for HashTag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHashTag extends EntityPathBase<HashTag> {

    private static final long serialVersionUID = -1182456412L;

    public static final QHashTag hashTag = new QHashTag("hashTag");

    public final ListPath<com.wanted.sns_feed_service.feed.entity.Feed, com.wanted.sns_feed_service.feed.entity.QFeed> feeds = this.<com.wanted.sns_feed_service.feed.entity.Feed, com.wanted.sns_feed_service.feed.entity.QFeed>createList("feeds", com.wanted.sns_feed_service.feed.entity.Feed.class, com.wanted.sns_feed_service.feed.entity.QFeed.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    public QHashTag(String variable) {
        super(HashTag.class, forVariable(variable));
    }

    public QHashTag(Path<? extends HashTag> path) {
        super(path.getType(), path.getMetadata());
    }

    public QHashTag(PathMetadata metadata) {
        super(HashTag.class, metadata);
    }

}

