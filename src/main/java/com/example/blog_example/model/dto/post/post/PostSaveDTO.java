package com.example.blog_example.model.dto.post.post;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@AllArgsConstructor
@Getter
public class PostSaveDTO {
    @Positive
    private Long upperCategoryNo;
    @Positive
    private Long lowerCategoryNo;
    @Positive
    private Long userNo;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
}
