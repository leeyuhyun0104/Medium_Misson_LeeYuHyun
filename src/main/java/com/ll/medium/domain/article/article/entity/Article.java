package com.ll.medium.domain.article.article.entity;

import com.ll.medium.domain.comment.comment.entity.Comment;
import com.ll.medium.domain.member.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String body;

    private LocalDateTime createDate;

    @ManyToOne
    private Member author;

    private LocalDateTime modifyDate;

    @Column(name = "IS_PUBLISHED")
    private Boolean isPublished;

    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE)
    private List<Comment> commentList;

    @Column(name = "IS_PAID")
    private Boolean isPaid;
}
