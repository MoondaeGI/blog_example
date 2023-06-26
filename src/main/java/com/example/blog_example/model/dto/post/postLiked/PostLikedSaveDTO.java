package com.example.blog_example.model.dto.post.postLiked;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Positive;

@AllArgsConstructor
@Getter
public class PostLikedSaveDTO {
    @Positive
    private Long postNo;
    @Positive
    private Long userNo;
}
