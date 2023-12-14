package com.ll.medium.domain.article.article.controller;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotEmpty;


@Getter
@Setter
public class CommentForm {
    @NotEmpty(message = "내용은 필수항목입니다.")
    private String body;
}
