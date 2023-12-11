package com.ll.medium.domain.article.article.service;

import com.ll.medium.DataNotFoundException;
import com.ll.medium.domain.article.article.entity.Article;
import com.ll.medium.domain.article.article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    public List<Article> getList() {
        return this.articleRepository.findAll(); // findAll 메서드로 articleList 생성
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

    public void create(String title, String body) {
        Article a = new Article();
        a.setTitle(title);
        a.setBody(body);
        a.setCreateDate(LocalDateTime.now());
        this.articleRepository.save(a);
    }


}

