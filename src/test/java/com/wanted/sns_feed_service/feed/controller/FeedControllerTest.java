package com.wanted.sns_feed_service.feed.controller;

import com.wanted.sns_feed_service.feed.entity.Feed;
import com.wanted.sns_feed_service.feed.repository.FeedRepository;
import com.wanted.sns_feed_service.hashTag.entity.HashTag;
import com.wanted.sns_feed_service.hashTag.repository.HashTagRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.wanted.sns_feed_service.feed.entity.Type.INSTAGRAM;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FeedControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    FeedRepository feedRepository;
    @Autowired
    HashTagRepository hashTagRepository;
    @DisplayName("해시 테그 검색 테스트, hashtag 가 정확하게 일치하지 않으면 데이터가 0건")
    @Test
    void 해시_테그_검색_실패() throws Exception {

        //when, then
        mockMvc.perform(get("/v1/feed")
                        .param("hashtag", "테스트")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content.size()").value(0))
                .andDo(print());
    }

    @DisplayName("해시 테그 검색 테스트")
    @Test
    void 해시_테그_검색() throws Exception {

        //when, then
        mockMvc.perform(get("/v1/feed")
                        .param("hashtag", "테스트태그1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content.size()").value(10))
                .andDo(print());
    }

    @DisplayName("타입_검색 테스트")
    @Test
    void 타입_검색() throws Exception {

        //when, then
        mockMvc.perform(get("/v1/feed")
                        .param("type", "INSTAGRAM")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content.size()").value(10))
                .andDo(print());
    }

    @DisplayName("정렬 테스트 - created_at, 내림차순 ")
    @Test
    void 정렬_테스트1() throws Exception {

        //when, then
        mockMvc.perform(get("/v1/feed")
                        .param("order_by", "DESC") // 가장 최근에 생성한 피드가 가장 위에 오게 됨.
                        .param("order_type", "created_at")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].contentId").value("threads9"))
                .andDo(print());
    }

    @DisplayName("정렬 테스트 - created_at, 오름차순 ")
    @Test
    void 정렬_테스트2() throws Exception {

        //when, then
        mockMvc.perform(get("/v1/feed")
                        .param("order_by", "asc") // 가장 오래전에 생성한 피드가 가장 위에 오게 됨.
                        .param("order_type", "created_at")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].contentId").value("instagram0"))
                .andDo(print());
    }

    @DisplayName("검색어 테스트")
    @Test
    void 검색어_테스트() throws Exception {

        //when, then
        mockMvc.perform(get("/v1/feed")
                        .param("search_by", "title") // 가장 오래전에 생성한 피드가 가장 위에 오게 됨.
                        .param("search", "9")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content.size()").value(4)) // title 에 9가 들어간건 4개 뿐임
                .andDo(print());
    }


    @DisplayName("검색어 테스트 - search_by 를 생략하면 title + content 으로 검색")
    @Test
    @Transactional
    void 검색어_테스트2() throws Exception {

        // given

        // 태그 추가
        HashTag tag = HashTag.builder()
                .name("instagram")
                .build();
        hashTagRepository.save(tag);

        // 피드 추가
        Feed insta1 = Feed.builder().contentId("instagram100").type(INSTAGRAM).title("맛있는").content("꿔바로우").build();
        Feed insta2 = Feed.builder().contentId("instagram200").type(INSTAGRAM).title("꿔바로우").content("좋아하세요?").build();

        insta1.addTag(tag);
        insta2.addTag(tag);

        feedRepository.save(insta1);
        feedRepository.save(insta2);

        //when, then
        mockMvc.perform(get("/v1/feed")
                        .param("search", "꿔바로우")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content.size()").value(2))
                .andDo(print());
    }

    @DisplayName("검색 테스트")
    @Test
    void 검색_테스트() throws Exception {

        //when, then
        mockMvc.perform(get("/v1/feed")
                        .param("hashtag", "테스트태그3")
                        .param("search", "9")
                        .param("order_by","desc")
                        .param("page_count","1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.totalElements").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.totalPages").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].contentId").value("threads9"))
                .andDo(print());
    }
}