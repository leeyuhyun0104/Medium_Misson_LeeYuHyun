package com.ll.medium.domain.article.article.controller;

import com.ll.medium.domain.article.article.entity.Article;
import com.ll.medium.domain.article.article.entity.ArticleForm;
import com.ll.medium.domain.article.article.service.ArticleService;
import com.ll.medium.domain.comment.comment.entity.CommentForm;
import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.domain.member.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class ArticleController {
    private final ArticleService articleService;
    private final MemberService memberService;

    @GetMapping("post/list")
    public String list(Model model, @RequestParam(value="page", defaultValue="0") int page) {
        Page<Article> paging = this.articleService.getList(page); // getList 메서드 사용해서 articleList 조회
        model.addAttribute("paging", paging); // Model 객체에 "articleList"라는 이름으로 값을 저장
        return "domain/article/article/article_list";
    }

    @GetMapping(value = "post/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, CommentForm commentForm, Principal principal) {
        Article article = this.articleService.getArticle(id); // ArticleService의 getArticle 메서드 호출
        if (!article.getIsPublished()) { // 비공개 글인 경우
            if (principal == null) { // 로그인한 사용자가 아니라면
                return "redirect:/member/login"; // 로그인 페이지로 리다이렉트
            }
        }
        model.addAttribute("article", article);
        return "domain/article/article/article_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("post/write")
    public String articleCreate(ArticleForm articleForm) {
        articleForm.setIsPublished(true); // 새 글 작성 시 기본값을 공개로 설정
        return "domain/article/article/article_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("post/write")
    public String articleCreate(@Valid ArticleForm articleForm, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()){
            return "domain/article/article/article_form"; // 오류가 있는 경우 글 작성 폼으로
        }
        Member member = this.memberService.getMember(principal.getName());
        this.articleService.create(articleForm.getTitle(), articleForm.getBody(), member, articleForm.getIsPublished()); //ArticleService 호출해서 새 글 저장
        return "redirect:/post/list"; // 저장 후 목록으로 이동
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("post/{id}/modify")
    public String articleModify(ArticleForm articleForm, @PathVariable("id") Integer id, Principal principal) {
        Article article = this.articleService.getArticle(id);
        if(!article.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        articleForm.setTitle(article.getTitle());
        articleForm.setBody(article.getBody());
        return "domain/article/article/article_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("post/{id}/modify")
    public String articleModify(@Valid ArticleForm articleForm, BindingResult bindingResult,
                                 Principal principal, @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "domain/article/article/article_form";
        }
        Article article = this.articleService.getArticle(id);
        if (!article.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.articleService.modify(article, articleForm.getTitle(), articleForm.getBody(), articleForm.getIsPublished());
        return String.format("redirect:/post/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("post/{id}/delete")
    public String articleDelete(Principal principal, @PathVariable("id") Integer id){
        Article article = this.articleService.getArticle(id);
        if(!article.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.articleService.delete(article);
        return "redirect:/post/list";
    }

    @GetMapping("post/myList")
    public String myArticles(Model model, Principal principal) {
        String username = principal.getName(); // 현재 로그인한 사용자의 username을 가져옴
        List<Article> myArticles = this.articleService.getMyArticles(username); // 사용자의 작성 글 목록을 가져옴
        model.addAttribute("myArticles", myArticles); // Model 객체에 "myArticles"라는 이름으로 값을 저장
        return "domain/article/article/my_articles";
    }

    @GetMapping("/b/{username}")
    public String userArticles(Model model, @PathVariable("username") String username) {
        List<Article> userArticles = this.articleService.getUserArticles(username);
        model.addAttribute("userArticles", userArticles);
        return "domain/article/article/user_articles";
    }

    @GetMapping("/b/{username}/{id}")
    public String userArticleDetail(Model model, @PathVariable("username") String username, @PathVariable("id") Integer id, Principal principal) {
        Article article = this.articleService.getUserArticle(username, id);
        if (!article.getIsPublished()) { // 비공개 글인 경우
            if (principal == null) { // 로그인한 사용자가 아니라면
                return "redirect:/member/login"; // 로그인 페이지로 리다이렉트
            }
        }
        model.addAttribute("article", article);
        return "domain/article/article/user_article_detail";
    }
}
