package com.example.blog_example.model.dto.post.post;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Positive;

@AllArgsConstructor
@Getter
public class PostFindDTO {
    @Positive
    private Long postNo;
}
