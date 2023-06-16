package com.example.blog_example.model.dto.post.detail;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@AllArgsConstructor
@Getter
public class PostDetailUpdateDTO {
    @Positive
    private Long postDetailNo;
    @NotBlank
    private String title;
    @NotNull
    private String content;
}
