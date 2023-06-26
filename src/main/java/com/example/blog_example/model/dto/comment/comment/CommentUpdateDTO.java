package com.example.blog_example.model.dto.comment.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@AllArgsConstructor
@Getter
public class CommentUpdateDTO {
    @Positive
    private Long commentNo;
    @NotBlank
    private String content;
}
