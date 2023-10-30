package com.wanted.sns_feed_service.feed.controller;

import com.wanted.sns_feed_service.feed.FeedService;
import com.wanted.sns_feed_service.feed.entity.Feed;
import com.wanted.sns_feed_service.feed.entity.Type;
import com.wanted.sns_feed_service.response.FeedDetailResponse;
import com.wanted.sns_feed_service.response.ResResult;
import com.wanted.sns_feed_service.response.ResponseCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/feed")
@Tag(name = "FeedController", description = "피드 컨트롤러")
public class FeedController {

    private final FeedService feedService;

    /**
     * 피드 검색
     */
    @GetMapping("")
    @Operation(summary = "피드 목록", description = "조건에 따른 피드 검색을 하고, 피드 목록을 불러 옵니다.")
    public ResponseEntity<ResResult> search(
            @RequestParam(value = "hashtag", required = false) String hashtag, // 정확히 일치하는 값 조회, 미 입력시 본인 계정
            @RequestParam(value = "type", required = false) Type type, // 미입력 시 모든 type 조회
            @RequestParam(value = "order_by", required = false, defaultValue = "DESC") String orderBy, // desc, asc
            @RequestParam(value = "order_target", required = false, defaultValue = "created_at") String orderTarget, // 정렬 기준이며, created_at,updated_at,like_count,share_count,view_count 가 사용 가능, 오름차순 , 내림차순 모두 가능
            @RequestParam(value = "search_by", required = false) String searchBy, // 검색 기준이며, title , content, title,content 이 사용, 미입력 시  title,content
            @RequestParam(value = "search", required = false) String searchKeyword, // 검색할 키워드 title 또는 content 또는 title + content 검색
            @RequestParam(value = "page", defaultValue = "0") int pageNumber, // 조회하려는 페이지
            @RequestParam(value = "page_count", defaultValue = "10") int pageSize // 페이지 당 게시물 갯수
            //TODO : username 받기
    ) {

        ResponseCode feedSearchCode = ResponseCode.FEED_SEARCH;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        return ResponseEntity.ok(
                ResResult.builder()
                        .responseCode(feedSearchCode)
                        .code(feedSearchCode.getCode())
                        .message(feedSearchCode.getMessage())
                        .data(feedService.search(hashtag, type, searchBy, orderBy, orderTarget, searchKeyword, pageable))
                        .build()

        );
    }

    /**
     * 피드 상세 조회
     */
    @GetMapping("/{id}")
    @Operation(summary = "피드 상세 조회", description = "피드 상세 정보를 조회하고, viewCount가 1 증가합니다.")
    public ResponseEntity<FeedDetailResponse> findFeed(@PathVariable long id) {
        Feed feed = feedService.findById(id);

        return ResponseEntity.ok().body(new FeedDetailResponse(feed));
    }

    /**
     * 피드 좋아요
     */
    @PutMapping("/like/{id}")
    @Operation(summary = "피드 좋아요", description = "외부 서비스의 피드 좋아요 endpoint를 호출합니다. likeCount가 1 증가합니다.")
    public ResponseEntity<FeedDetailResponse> likeFeed(@PathVariable long id) {
        Feed feed = feedService.likeById(id);

        return ResponseEntity.ok().body(new FeedDetailResponse(feed));
    }

    /**
     * 피드 공유
     */
    @PutMapping("/share/{id}")
    @Operation(summary = "피드 공유", description = "외부 서비스의 피드 공유 endpoint를 호출합니다. shareCount가 1 증가합니다.")
    public ResponseEntity<FeedDetailResponse> shareFeed(@PathVariable long id) {
        Feed feed = feedService.shareById(id);

        return ResponseEntity.ok().body(new FeedDetailResponse(feed));
    }
}