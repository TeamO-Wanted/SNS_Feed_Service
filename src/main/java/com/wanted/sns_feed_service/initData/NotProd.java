package com.wanted.sns_feed_service.initData;

import static com.wanted.sns_feed_service.feed.entity.Type.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.wanted.sns_feed_service.feed.entity.Feed;
import com.wanted.sns_feed_service.feed.repository.FeedRepository;
import com.wanted.sns_feed_service.hashTag.entity.HashTag;
import com.wanted.sns_feed_service.hashTag.repository.HashTagRepository;
import com.wanted.sns_feed_service.member.entity.Member;
import com.wanted.sns_feed_service.member.repository.MemberRepository;
import com.wanted.sns_feed_service.util.Ut;

@Configuration
@Profile({"dev", "test"})
public class NotProd {
	@Bean
	CommandLineRunner initData(MemberRepository memberRepository, FeedRepository feedRepository,
		HashTagRepository hashTagRepository) {

		String password = Ut.encrypt.encryptPW("1234");
		return args -> {
			List<Member> memberList = new ArrayList<>();
			Member user1 = Member.builder()
				.account("user1")
				.password(password)
				.email("user1@test.com")
				.build();

			Member user2 = Member.builder()
				.account("user2")
				.password(password)
				.email("user2@test.com")
				.build();

			Member user3 = Member.builder()
				.account("user3")
				.password(password)
				.email("user3@test.com")
				.build();

			memberList.addAll(List.of(user1, user2, user3));
			memberRepository.saveAll(memberList);

			List<HashTag> hashTagList = new ArrayList<>();

			HashTag tag1 = HashTag.builder()
				.name("테스트태그1")
				.build();

			HashTag tag2 = HashTag.builder()
				.name("테스트태그2")
				.build();

			HashTag tag3 = HashTag.builder()
				.name("테스트태그3")
				.build();

			HashTag tag4 = HashTag.builder()
				.name("테스트태그3")
				.build();

			hashTagList.addAll(List.of(tag1, tag2, tag3, tag4));
			hashTagRepository.saveAll(hashTagList);

			// 피드 생성
			List<Feed> feedList = new ArrayList<>();
			for (int i = 0; i < 10; i++) {
				Feed instaTmp = Feed.builder()
					.contentId("instagram" + i)
					.type(INSTAGRAM)
					.content("인스타 테스트 피드" + i)
					.title("인스타 테스트 타이틀" + i)
					.build();
				// 태그 추가
				instaTmp.addTag(tag1);
				// 리스트 추가
				feedList.add(instaTmp);
			}

			for (int i = 0; i < 10; i++) {
				Feed facebookTmp = Feed.builder()
					.contentId("facebook" + i)
					.type(FACEBOOK)
					.content("페이스북 테스트 피드" + i)
					.title("페이스북 테스트 타이틀" + i)
					.build();
				// 태그 추가
				facebookTmp.addTag(tag2);
				// 리스트 추가
				feedList.add(facebookTmp);
			}

			for (int i = 0; i < 10; i++) {
				Feed twitterTmp = Feed.builder()
					.contentId("twitter" + i)
					.type(TWITTER)
					.content("트위터 테스트 피드" + i)
					.title("트위터 테스트 타이틀" + i)
					.build();
				// 태그 추가
				twitterTmp.addTag(tag3);
				// 리스트 추가
				feedList.add(twitterTmp);
			}

			for (int i = 0; i < 10; i++) {
				Feed threadsTmp = Feed.builder()
					.contentId("threads" + i)
					.type(THREADS)
					.content("쓰레드 테스트 피드" + i)
					.title("쓰레드 테스트 타이틀" + i)
					.build();
				// 태그 추가
				threadsTmp.addTag(tag4);
				// 리스트 추가
				feedList.add(threadsTmp);
			}

			feedRepository.saveAll(feedList);
		};
	}
}