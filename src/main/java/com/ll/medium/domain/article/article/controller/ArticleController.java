package com.ll.medium.domain.article.article.controller;

import com.ll.medium.domain.article.article.entity.Article;
import com.ll.medium.domain.article.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
@RequestMapping("/article")
@RequiredArgsConstructor
@Controller
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping("/list")
    public String list(Model model) {
        List<Article> articleList = this.articleService.getList(); // getList 메서드 사용해서 articleList 조회
        model.addAttribute("articleList", articleList); // Model 객체에 "articleList"라는 이름으로 값을 저장
        return "domain/article/article/article_list";
    }

    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        Article article = this.articleService.getArticle(id); // ArticleService의 getArticle 메서드 호출
        model.addAttribute("article", article);
        return "domain/article/article/article_detail";
    }
}
