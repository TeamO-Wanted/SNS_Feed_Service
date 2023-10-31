package com.wanted.sns_feed_service;

import jakarta.persistence.EntityListeners;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync // 비동기 기능 활성화
public class SnsFeedServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SnsFeedServiceApplication.class, args);
    }

}
