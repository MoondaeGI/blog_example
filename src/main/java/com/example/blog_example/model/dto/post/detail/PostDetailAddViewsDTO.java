package com.example.blog_example.model.dto.post.detail;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Positive;

@AllArgsConstructor
@Getter
public class PostDetailAddViewsDTO {
    @Positive
    private Long postDetailNo;
}
