package com.ll.medium.domain.article.article.controller;

import com.ll.medium.domain.article.article.entity.Article;
import com.ll.medium.domain.article.article.service.ArticleService;
import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.domain.member.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequestMapping("/post")
@RequiredArgsConstructor
@Controller
public class ArticleController {
    private final ArticleService articleService;
    private final MemberService memberService;

    @GetMapping("/list")
    public String list(Model model, @RequestParam(value="page", defaultValue="0") int page) {
        Page<Article> paging = this.articleService.getList(page); // getList 메서드 사용해서 articleList 조회
        model.addAttribute("paging", paging); // Model 객체에 "articleList"라는 이름으로 값을 저장
        return "domain/article/article/article_list";
    }

    @GetMapping(value = "/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        Article article = this.articleService.getArticle(id); // ArticleService의 getArticle 메서드 호출
        model.addAttribute("article", article);
        return "domain/article/article/article_detail";
    }

    @GetMapping("/write")
    public String articleCreate(Article article) {
        return "domain/article/article/article_form";
    }

    @PostMapping("/write")
    public String articleCreate(@Valid Article article, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()){
            return "domain/article/article/article_form"; // 오류가 있는 경우 글 작성 폼으로
        }
        Member member = this.memberService.getMember(principal.getName());
        this.articleService.create(article.getTitle(), article.getBody(), member); //ArticleService 호출해서 새 글 저장
        return "redirect:/post/list"; // 저장 후 목록으로 이동
    }
}
