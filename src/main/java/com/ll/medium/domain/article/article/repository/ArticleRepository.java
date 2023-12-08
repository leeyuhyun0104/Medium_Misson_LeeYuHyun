package com.ll.medium.domain.article.article.repository;

import com.ll.medium.domain.article.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
    Article findBySubject(String subject); // 제목으로 데이터 조회하는 메서드
    Article findBySubjectAndContent(String subject, String content); // 제목과 내용으로 조회
    List<Article> findBySubjectLike(String subject); // 제목에 특정 문자열 포함하는 데이터 조회
}