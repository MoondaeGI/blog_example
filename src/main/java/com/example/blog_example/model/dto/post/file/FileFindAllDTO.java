package com.example.blog_example.model.dto.post.file;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Positive;

@AllArgsConstructor
@Getter
public class FileFindAllDTO {
    @Positive
    private Long postDetailNo;
}
