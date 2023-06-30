package com.example.blog_example.model.dto.post.liked;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Positive;

@Builder
@AllArgsConstructor
@Getter
public class PostLikedSaveDTO {
    @Positive
    private Long postNo;
    @Positive
    private Long userNo;
}
