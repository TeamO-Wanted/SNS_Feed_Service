package com.wanted.sns_feed_service.feed;

import com.wanted.sns_feed_service.feed.entity.Feed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class WebClientService {

    public void sendSnsApi(String path, Feed feed) {
        WebClient.builder()
                .baseUrl(feed.getType().getUrl())
                // Request Header 추가
                .filter(
                        (request, next) -> next.exchange(
                                ClientRequest.from(request).header("from", "webclient").build()
                        ))
                // Request Header 로깅
                .filter(
                    ExchangeFilterFunction.ofRequestProcessor(
                            clientRequest -> {
                                log.info("================ Request ================");
                                log.info("Request: {} {}", clientRequest.method(), clientRequest.url());
                                clientRequest.headers().forEach(
                                        (name, values) -> values.forEach(value -> log.info("{} : {}", name, value))
                                );
                                return Mono.just(clientRequest);
                            }
                    )
                )
                // Response Header 로깅
                .filter(
                        ExchangeFilterFunction.ofResponseProcessor(
                                clientResponse -> {
                                    log.info("================ Response ================");
                                    clientResponse.headers().asHttpHeaders().forEach((name, values) -> values.forEach(value -> log.info("{} : {}", name, value)));
                                    return Mono.just(clientResponse);
                                }
                        )
                )
                .build()
                .post()
                .uri(path + "/" + feed.getContentId())
                .retrieve()
                .bodyToMono(String.class);
    }
}
