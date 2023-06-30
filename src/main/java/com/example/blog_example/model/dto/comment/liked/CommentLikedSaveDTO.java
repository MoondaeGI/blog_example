package com.example.blog_example.model.dto.comment.liked;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Positive;

@Builder
@AllArgsConstructor
@Getter
public class CommentLikedSaveDTO {
    @Positive
    private Long commentNo;
    @Positive
    private Long userNo;
}
