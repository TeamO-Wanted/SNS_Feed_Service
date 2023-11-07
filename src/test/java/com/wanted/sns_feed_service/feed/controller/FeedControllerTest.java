package com.wanted.sns_feed_service.feed.controller;

import static com.wanted.sns_feed_service.feed.entity.Type.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.wanted.sns_feed_service.feed.entity.Feed;
import com.wanted.sns_feed_service.feed.repository.FeedRepository;
import com.wanted.sns_feed_service.hashTag.entity.HashTag;
import com.wanted.sns_feed_service.hashTag.repository.HashTagRepository;
import com.wanted.sns_feed_service.member.entity.Member;
import com.wanted.sns_feed_service.member.repository.MemberRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
@Transactional
class FeedControllerTest {

	@Autowired
	MockMvc mockMvc;
	@Autowired
	FeedRepository feedRepository;
	@Autowired
	HashTagRepository hashTagRepository;

	@Autowired
	MemberRepository memberRepository;

    Member user1;
    String user1Token;

	@BeforeEach
	void init() {
		// given

		// 태그 추가
		HashTag tag = HashTag.builder()
			.name("테스트테그3")
			.build();
		hashTagRepository.save(tag);

		// 피드 추가
		Feed insta = Feed.builder().contentId("test").type(INSTAGRAM).title("test").content("test").build();

		insta.addTag(tag);

		feedRepository.save(insta);

        user1 = memberRepository.findByAccount("user1").get();
        user1Token = user1.getAccessToken();
	}

	@DisplayName("해시 테그 검색 테스트, hashtag 가 정확하게 일치하지 않으면 데이터가 0건")
	@Test
	void 해시_테그_검색_실패() throws Exception {

		//when, then
		mockMvc.perform(get("/feed")
				.header("Authorization", "Bearer " + user1Token) // 헤더에 Authorization 값을 추가
				.param("hashtag", "테스트")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.content.size()").value(0))
			.andDo(print());
	}

	@DisplayName("해시 테그 검색 테스트")
	@Test
	void 해시_테그_검색() throws Exception {

		//when, then
		mockMvc.perform(get("/feed")
				.header("Authorization", "Bearer " + user1Token) // 헤더에 Authorization 값을 추가
				.param("hashtag", "테스트태그1")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.content.size()").value(10))
			.andDo(print());
	}

	@DisplayName("타입_검색 테스트")
	@Test
	void 타입_검색() throws Exception {

		//when, then
		mockMvc.perform(get("/feed")
				.header("Authorization", "Bearer " + user1Token) // 헤더에 Authorization 값을 추가
				.param("type", "INSTAGRAM")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.content.size()").value(10))
			.andExpect(jsonPath("$.data.content[0].type").value("INSTAGRAM"))
			.andDo(print());
	}

	@DisplayName("정렬 테스트 - created_at, 내림차순 ")
	@Test
	void 정렬_테스트1() throws Exception {

		//when, then
		mockMvc.perform(get("/feed")
				.header("Authorization", "Bearer " + user1Token) // 헤더에 Authorization 값을 추가
				.param("order_by", "DESC") // 가장 최근에 생성한 피드가 가장 위에 오게 됨.
				.param("order_type", "created_at")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.content[0].content").value("test"))
			.andDo(print());
	}

	@DisplayName("정렬 테스트 - created_at, 오름차순 ")
	@Test
	void 정렬_테스트2() throws Exception {

		//when, then
		mockMvc.perform(get("/feed")
				.header("Authorization", "Bearer " + user1Token) // 헤더에 Authorization 값을 추가
				.param("order_by", "asc") // 가장 오래전에 생성한 피드가 가장 위에 오게 됨.
				.param("order_type", "created_at")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.content[0].content").value("인스타 테스트 피드0"))
			.andDo(print());
	}

	@DisplayName("검색어 테스트")
	@Test
	void 검색어_테스트() throws Exception {

		//when, then
		mockMvc.perform(get("/feed")
				.header("Authorization", "Bearer " + user1Token) // 헤더에 Authorization 값을 추가
				.param("search_by", "title") // title 로 검색
				.param("search", "9")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.content.size()").value(4)) // title 에 9가 들어간건 4개 뿐임
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
		mockMvc.perform(get("/feed")
				.header("Authorization", "Bearer " + user1Token) // 헤더에 Authorization 값을 추가
				.param("search", "꿔바로우")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.content.size()").value(2))
			.andDo(print());
	}

	@DisplayName("글자수 20자 제한 테스트")
	@Test
	@Transactional
	void 글자수_20자_제한_테스트() throws Exception {

		// given

		// 태그 추가
		HashTag tag = HashTag.builder()
			.name("타짜")
			.build();
		hashTagRepository.save(tag);

		// 피드 추가
		String longContent = "싸늘하다. 가슴에 비수가 날아와 꽂힌다. 하지만 걱정하지 마라. 손은 눈보다 빠르니까.";
		String shortContent = "묻고 더블로 가!";

		Feed insta1 = Feed.builder().contentId("타짜1").type(INSTAGRAM).title("타짜")
			.content(longContent).build();

		Feed insta2 = Feed.builder().contentId("타짜2").type(INSTAGRAM).title("타짜")
			.content(shortContent).build();

		insta1.addTag(tag);
		insta2.addTag(tag);

		feedRepository.save(insta1);
		feedRepository.save(insta2);

		// when, then
		mockMvc.perform(get("/feed")
				.header("Authorization", "Bearer " + user1Token) // 헤더에 Authorization 값을 추가
				.param("hashtag", "타짜") // tag 로 검색
				.param("order_target", "create_at") // 생성순
				.param("order_by", "asc") // 먼저 생성된 데이터가 가장 상단에 위치
				.contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].content") // 목록의 첫번째 content 를 가져옴
				.value(longContent.substring(0, 20) + "...")) // 글자 수가 20을 넘어가면 이후 글자는 생략됨.
			.andExpect(MockMvcResultMatchers.jsonPath("$.data.content[1].content")
				.value(shortContent)) // 글자 수가 20을 넘어가지 않을 때는 생략 없음.
			.andDo(print());
	}

	@DisplayName("통합 검색 테스트")
	@Test
	void 통합_검색_테스트() throws Exception {

		//when, then
		mockMvc.perform(get("/feed")
				.header("Authorization", "Bearer " + user1Token) // 헤더에 Authorization 값을 추가
				.param("hashtag", "테스트테그3") // 해시테그로 검색
				.param("search", "test") // 검색어
				.param("order_by", "desc") // 정렬 순서
				.param("page_count", "1") // 하나의 page 에 담길 데이터 수
				.contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.totalElements").value(1)) // 총 검색된 데이터 건 수
			.andExpect(jsonPath("$.data.totalPages").value(1)) // 총 페이지 수
			.andExpect(jsonPath("$.data.content[0].content").value("test"))
			.andDo(print());
	}

	@DisplayName("통합 검색 테스트2")
	@Test
	void 통합_검색_테스트2() throws Exception {

		//when, then
		mockMvc.perform(get("/feed")
				.header("Authorization", "Bearer " + user1Token) // 헤더에 Authorization 값을 추가
				.param("type", "INSTAGRAM") // 타입으로 검색
				.param("search_by", "title") // 타이틀로 검색
				.param("search", "틀0") // 검색어
				.contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.totalElements").value(1)) // 총 검색된 데이터 건 수
			.andExpect(jsonPath("$.data.content[0].title").value("인스타 테스트 타이틀0"))
			.andDo(print());
	}

	@DisplayName("findFeed: 게시물 상세 조회에 성공한다.")
	@Test
	public void findFeed() throws Exception {
		// given
		final String url = "/feed/{id}";

		// when
		final ResultActions resultActions = mockMvc.perform(get(url, 1)
			.header("Authorization", "Bearer " + user1Token) // 헤더에 Authorization 값을 추가
		);

		// then
		resultActions.andExpect(status().isOk())
			.andExpect(jsonPath("$.contentId").value("instagram0"))
			.andExpect(jsonPath("$.viewCount").value(1));
	}
}