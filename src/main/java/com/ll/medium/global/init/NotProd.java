package com.ll.medium.global.init;

import com.ll.medium.domain.article.article.service.ArticleService;
import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.domain.member.member.service.MemberService;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class NotProd {
    private static final Logger log = LoggerFactory.getLogger(NotProd.class);
    private final MemberService memberService;
    private final ArticleService articleService;

    @Bean
    public ApplicationRunner initNotProd() {
        return args -> {
            createSampleMembers();
            createSampleArticles();
        };
    }

    private void createSampleMembers() {
        int totalMembersToCreate = 100;
        int freeMembersToCreate = totalMembersToCreate / 2; // 무료 회원 수
        int paidMembersToCreate = totalMembersToCreate - freeMembersToCreate; // 유료 회원 수

        // 무료 회원 생성
        for (int i = 1; i < freeMembersToCreate+1; i++) {
            String username = "user" + i;
            String password = "1234";
            try {
                memberService.join(username, password, false); // 무료 멤버십으로 회원가입
            } catch (Exception e) {
                log.error("Failed to create sample free membership: {}", e.getMessage());
            }
        }

        // 유료 회원 생성
        for (int i = 1; i < paidMembersToCreate+1; i++) {
            String username = "paidUser" + i;
            String password = "1234";
            try {
                memberService.join(username, password, true); // 유료 멤버십으로 회원가입
            } catch (Exception e) {
                log.error("Failed to create sample paid membership: {}", e.getMessage());
            }
        }
    }

    // 샘플 데이터 생성 메서드
    private void createSampleArticles() {
        int totalArticlesToCreate = 100;
        int freeArticlesToCreate = totalArticlesToCreate / 2; // 무료글 수
        int paidArticlesToCreate = totalArticlesToCreate - freeArticlesToCreate; // 유료글 수

        Member user = memberService.getMember("user1");

        // 무료글 생성
        for (int i = 1; i <= freeArticlesToCreate; i++) {
            String title = "무료글 " + i;
            String body = "무료글 본문 " + i;
            try {
                articleService.create(title, body, user, true, false); // 무료글 생성
            } catch (Exception e) {
                log.error("Failed to create sample free articles: {}", e.getMessage());
            }
        }

        // 유료글 생성
        for (int i = 1; i <= paidArticlesToCreate; i++) {
            String title = "유료글 " + i;
            String body = "유료글 본문 " + i;
            try {
                articleService.create(title, body, user, false, true); // 유료글 생성
            } catch (Exception e) {
                log.error("Failed to create sample paid articles: {}", e.getMessage());
            }
        }
    }
}

