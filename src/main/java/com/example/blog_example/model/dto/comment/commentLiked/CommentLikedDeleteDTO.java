package com.example.blog_example.model.dto.comment.commentLiked;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Positive;

@AllArgsConstructor
@Getter
public class CommentLikedDeleteDTO {
    @Positive
    private Long commentNo;
}
