package com.example.blog_example.model.dto.comment.liked;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Positive;

@AllArgsConstructor
@Getter
public class CommentLikedCountDTO {
    @Positive
    private Long commentNo;
}