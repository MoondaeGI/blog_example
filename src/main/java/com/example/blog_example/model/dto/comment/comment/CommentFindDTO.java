package com.example.blog_example.model.dto.comment.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Positive;

@Builder
@AllArgsConstructor
@Getter
public class CommentFindDTO {
    @Positive
    private Long commentNo;
}
