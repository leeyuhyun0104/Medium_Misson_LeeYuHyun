package com.ll.medium.domain.article.article.repository;

import com.ll.medium.domain.article.article.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
