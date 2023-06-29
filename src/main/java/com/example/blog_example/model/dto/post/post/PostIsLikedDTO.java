package com.example.blog_example.model.dto.post.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Positive;

@Builder
@AllArgsConstructor
@Getter
public class PostIsLikedDTO {
    @Positive
    private Long postNo;
}
