package com.wanted.sns_feed_service.hashTag.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wanted.sns_feed_service.hashTag.entity.HashTag;

public interface HashTagRepository extends JpaRepository<HashTag, String> {
}
