package com.ll.medium.domain.article.article.controller;

import com.ll.medium.domain.article.article.entity.Article;
import com.ll.medium.domain.article.article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ArticleController {
    private final ArticleRepository articleRepository;

    @GetMapping("/article/list")
    public String list(Model model) {
        List<Article> articleList = this.articleRepository.findAll(); // findAll 메서드 사용해서 articleList 생성
        model.addAttribute("articleList", articleList); // Model 객체에 "articleList"라는 이름으로 값을 저장
        return "domain/article/article/article_list";
    }
}
