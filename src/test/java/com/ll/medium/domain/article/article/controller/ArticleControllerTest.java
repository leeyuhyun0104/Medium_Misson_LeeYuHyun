package com.ll.medium.domain.article.article.controller;

import com.ll.medium.domain.article.article.entity.Article;
import com.ll.medium.domain.article.article.repository.ArticleRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class ArticleControllerTest {
    @Autowired
    private ArticleRepository articleRepository;

    @Test
    void testJpa() {
        Article a1 = new Article();
        a1.setSubject("sbb가 무엇인가요?");
        a1.setContent("sbb에 대해서 알고 싶습니다.");
        a1.setCreateDate(LocalDateTime.now());
        this.articleRepository.save(a1);  // 첫번째 질문 저장

        Article a2 = new Article();
        a2.setSubject("스프링부트 모델 질문입니다.");
        a2.setContent("id는 자동으로 생성되나요?");
        a2.setCreateDate(LocalDateTime.now());
        this.articleRepository.save(a2);  // 두번째 질문 저장
    }
}
