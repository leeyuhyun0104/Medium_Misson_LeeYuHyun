package com.ll.medium.domain.home.home.controller;

import com.ll.medium.domain.article.article.entity.Article;
import com.ll.medium.domain.article.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final ArticleService articleService;
    @GetMapping("/")
    public String showMain(Model model) {
        List<Article> latestArticles = articleService.getLatestArticles();
        model.addAttribute("latestArticles", latestArticles);
        return "domain/home/home/main";
    }
}
