package com.ll.medium.domain.comment.comment.service;

import com.ll.medium.DataNotFoundException;
import com.ll.medium.domain.article.article.entity.Article;
import com.ll.medium.domain.comment.comment.entity.Comment;
import com.ll.medium.domain.comment.comment.repository.CommentRepository;
import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.domain.member.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private MemberRepository memberRepository;

    public void create(Article article, String body, Member author) {
        Comment comment = new Comment();
        comment.setBody(body);
        comment.setCreateDate(LocalDateTime.now());
        comment.setArticle(article);
        comment.setAuthor(author);
        this.commentRepository.save(comment);
    }

    public Comment getComment(Integer id) {
        Optional<Comment> comment = this.commentRepository.findById(id);
        if (comment.isPresent()) {
            return comment.get();
        } else {
            throw new DataNotFoundException("comment not found");
        }
    }

    public void modify(Comment comment, String body) {
        comment.setBody(body);
        comment.setModifyDate(LocalDateTime.now());
        this.commentRepository.save(comment);
    }
}
