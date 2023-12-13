package com.ll.medium.domain.article.article.entity;

import com.ll.medium.domain.member.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
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
}
