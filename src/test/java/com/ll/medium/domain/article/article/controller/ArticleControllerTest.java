package com.ll.medium.domain.article.article.controller;

import com.ll.medium.domain.article.article.entity.Article;
import com.ll.medium.domain.article.article.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ArticleControllerTest {
    @Autowired
    private ArticleRepository articleRepository;

    @DisplayName("데이터 조회")
    @Test
    void testJpa() {
        Article a = this.articleRepository.findBySubjectAndContent("sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
        assertEquals(1, a.getId());
        }

    @DisplayName("제목에 특정 문자열 포함하는 데이터 조회")
    @Test
    void testJpa2() {
        List<Article> qList = this.articleRepository.findBySubjectLike("sbb%");
        Article a = qList.get(0);
        assertEquals("sbb가 무엇인가요?", a.getSubject());
    }

    @DisplayName("데이터 수정")
    @Test
    void testJpa3() {
        Optional<Article> oa = this.articleRepository.findById(1);
        assertTrue(oa.isPresent());
        Article a = oa.get();
        a.setSubject("수정된 제목");
        this.articleRepository.save(a);
    }

    @DisplayName("데이터 삭제")
    @Test
    void testJpa4(){
        assertEquals(2, this.articleRepository.count()); // 삭제 전 데이터가 2개인지 테스트
        Optional<Article> oa = this.articleRepository.findById(1);

        assertTrue(oa.isPresent());
        Article a = oa.get();
        this.articleRepository.delete(a);
        assertEquals(1, this.articleRepository.count()); // 삭제 후 1개인지 테스트
    }
}

