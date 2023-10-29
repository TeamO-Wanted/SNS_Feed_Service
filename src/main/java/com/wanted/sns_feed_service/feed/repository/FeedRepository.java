package com.wanted.sns_feed_service.feed.repository;

import com.wanted.sns_feed_service.feed.entity.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedRepository extends JpaRepository<Feed,Long>, FeedRepositoryCustom{
}
