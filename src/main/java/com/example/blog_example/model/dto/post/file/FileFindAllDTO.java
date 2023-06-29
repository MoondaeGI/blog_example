package com.example.blog_example.model.dto.post.file;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Positive;

@Builder
@AllArgsConstructor
@Getter
public class FileFindAllDTO {
    @Positive
    private Long postNo;
}
