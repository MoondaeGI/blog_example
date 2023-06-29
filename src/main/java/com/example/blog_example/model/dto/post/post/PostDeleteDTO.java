package com.example.blog_example.model.dto.post.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Positive;

@Builder
@AllArgsConstructor
@Getter
public class PostDeleteDTO {
    @Positive
    private Long postNo;
}
