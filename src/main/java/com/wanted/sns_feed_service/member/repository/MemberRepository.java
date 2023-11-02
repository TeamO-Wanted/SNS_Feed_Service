package com.wanted.sns_feed_service.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wanted.sns_feed_service.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByAccount(String account);
}
