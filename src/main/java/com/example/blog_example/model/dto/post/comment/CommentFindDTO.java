package com.example.blog_example.model.dto.post.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Positive;

@AllArgsConstructor
@Getter
public class CommentFindDTO {
    @Positive
    private Long commentNo;
}
