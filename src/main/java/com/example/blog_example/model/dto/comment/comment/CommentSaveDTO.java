package com.example.blog_example.model.dto.comment.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@AllArgsConstructor
@Getter
public class CommentSaveDTO {
    @NotBlank
    private String content;
    @Positive
    private Long userNo;
    @Positive
    private Long postNo;
}
