package com.wanted.sns_feed_service.member.repository;

import java.util.Optional;

import com.wanted.sns_feed_service.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {
	Optional<Member> findByAccount(String account);
}
