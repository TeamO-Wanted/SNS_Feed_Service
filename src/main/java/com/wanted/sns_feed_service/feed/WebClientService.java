package com.wanted.sns_feed_service.feed;

import ch.qos.logback.core.status.StatusManager;
import com.wanted.sns_feed_service.feed.entity.Feed;
import com.wanted.sns_feed_service.response.FeedDetailResponse;
import jdk.jshell.Snippet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
// 요구사항 기능 개발 요소. 단, Endpoint URL은 유효한 URL이 아님
// FIXME : 유효한 외부 Endpoint URL 제공 시 Type enum url 수정
public class WebClientService {

    public void sendSnsApi(String path, Feed feed) throws Exception {
        log.debug("================ webClient Request ================");
        WebClient webClient = WebClient.builder()
                .baseUrl(feed.getType().getUrl())
                .build();
        String response = webClient.post()
                .uri(path + "/" + feed.getContentId())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                // 외부 API 호출 에러 발생 시 에러메시지 리턴
                .onErrorReturn("[Error] " + feed.getContentId())
                .block();

        log.debug("response : " + response);
        if(response != null && response.equals(feed.getContentId())) {
            log.debug("Success");
        } else {
            throw new Exception("외부 API 호출 에러");
        }
    }
}
