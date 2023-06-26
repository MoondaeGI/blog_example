package com.example.blog_example.model.dto.comment.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Positive;

@AllArgsConstructor
@Getter
public class CommentFindByObjectDTO {
    @Positive
    private Long objectNo;
}
