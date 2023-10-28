package com.wanted.sns_feed_service.feed.controller;

import com.wanted.sns_feed_service.feed.repository.FeedRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FeedControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    FeedRepository feedRepository;

    @BeforeEach
    public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @DisplayName("findFeed: 게시물 상세 조회에 성공한다.")
    @Test
    public void findFeed() throws Exception {
        // given
        final String url = "/feeds/{id}";

        // when
        final ResultActions resultActions =mockMvc.perform(get(url, 1));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.contentId").value("instagram0"))
                .andExpect(jsonPath("$.viewCount").value(1));
    }

}