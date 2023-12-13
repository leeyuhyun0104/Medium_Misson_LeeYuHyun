package com.ll.medium.domain.article.article.service;

import com.ll.medium.DataNotFoundException;
import com.ll.medium.domain.article.article.entity.Article;
import com.ll.medium.domain.article.article.repository.ArticleRepository;
import com.ll.medium.domain.member.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    // 질문 목록 페이지
    public Page<Article> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.articleRepository.findAll(pageable);
    }

    // id값으로 Article 데이터를 조회하는 메서드
    public Article getArticle(Integer id) {
        Optional<Article> article = this.articleRepository.findById(id);
        if (article.isPresent()) {
            return article.get();
        } else {
            throw new DataNotFoundException("article not found");
        }
    }

    public void create(String title, String body, Member user, Boolean isPublished) {
        Article a = new Article();
        a.setTitle(title);
        a.setBody(body);
        a.setCreateDate(LocalDateTime.now());
        a.setAuthor(user);
        a.setIsPublished(isPublished);
        this.articleRepository.save(a);
    }

    public void modify(Article article, String title, String body) {
        article.setTitle(title);
        article.setBody(body);
        article.setModifyDate(LocalDateTime.now());
        this.articleRepository.save(article);
    }

    public void delete(Article article) {
        this.articleRepository.delete(article);
    }

    public List<Article> getLatestArticles() {
        // 최신 30개의 글을 가져오는 메서드 구현
        return articleRepository.findTop30ByOrderByCreateDateDesc();
    }

    public Page<Article> getAllArticles(boolean isPublished, int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.articleRepository.findByIsPublished(isPublished, pageable);
    }
}



