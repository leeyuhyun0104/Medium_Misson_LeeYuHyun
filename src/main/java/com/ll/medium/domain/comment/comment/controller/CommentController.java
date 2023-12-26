package com.ll.medium.domain.comment.comment.controller;

import com.ll.medium.domain.article.article.entity.Article;
import com.ll.medium.domain.article.article.service.ArticleService;
import com.ll.medium.domain.comment.comment.entity.Comment;
import com.ll.medium.domain.comment.comment.entity.CommentForm;
import com.ll.medium.domain.comment.comment.service.CommentService;
import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.domain.member.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class CommentController {

    private final ArticleService articleService;
    private final CommentService commentService;
    private final MemberService memberService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/post/{id}/comment/write")
    public String createComment(CommentForm commentForm) {
        return "domain/comment/comment/comment_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/post/{id}/comment/write")
    public String createComment(Model model, @PathVariable("id") Integer id, @Valid CommentForm commentForm, BindingResult bindingResult, Principal principal) {
        Article article = this.articleService.getArticle(id);
        Member member = this.memberService.getMember(principal.getName());
        if(bindingResult.hasErrors()){
            model.addAttribute("article", article);
            return "domain/comment/comment/comment_form";
        }
        this.commentService.create(article, commentForm.getBody(), member);
        return String.format("redirect:/post/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/post/{postId}/comment/{commentId}/modify")
    public String commentModify(CommentForm commentForm, @PathVariable("postId") Integer postId,
                                @PathVariable("commentId") Integer commentId, Principal principal) {
        Comment comment = this.commentService.getComment(commentId);
        if (!comment.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        commentForm.setBody(comment.getBody());
        return "domain/comment/comment/comment_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/post/{postId}/comment/{commentId}/modify")
    public String commentModify(@Valid CommentForm commentForm, BindingResult bindingResult,
                                @PathVariable("postId") Integer postId,
                                @PathVariable("commentId") Integer commentId,
                                Principal principal) {
        if (bindingResult.hasErrors()) {
            return "domain/comment/comment/comment_form";
        }
        Comment comment = this.commentService.getComment(commentId);
        if (!comment.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.commentService.modify(comment, commentForm.getBody());
        return String.format("redirect:/post/%s", comment.getArticle().getId());
    }
}
