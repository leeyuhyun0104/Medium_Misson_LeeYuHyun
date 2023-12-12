package com.ll.medium.domain.article.article.controller;

import com.ll.medium.domain.article.article.service.ArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ArticleControllerTest {
    @Autowired
    private ArticleService articleService;

    @Test
    void testJpa() {
        for (int i = 1; i <= 300; i++) {
            String title = String.format("테스트 데이터입니다:[%03d]", i);
            String body = "테스트";
            this.articleService.create(title, body, null);
        }
    }
}

