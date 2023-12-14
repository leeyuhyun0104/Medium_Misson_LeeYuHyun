package com.ll.medium.domain.article.article.service;

import com.ll.medium.domain.article.article.entity.Article;
import com.ll.medium.domain.article.article.entity.Comment;
import com.ll.medium.domain.article.article.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public void create(Article article, String body) {
        Comment comment = new Comment();
        comment.setBody(body);
        comment.setCreateDate(LocalDateTime.now());
        comment.setArticle(article);
        this.commentRepository.save(comment);
    }
}
