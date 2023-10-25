package com.wanted.sns_feed_service;

import jakarta.persistence.EntityListeners;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SnsFeedServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SnsFeedServiceApplication.class, args);
    }

}
