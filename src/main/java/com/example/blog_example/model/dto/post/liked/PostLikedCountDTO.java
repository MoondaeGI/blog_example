package com.example.blog_example.model.dto.post.liked;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Positive;

@AllArgsConstructor
@Getter
public class PostLikedCountDTO {
    @Positive
    private Long postNo;
}