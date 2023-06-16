package com.example.blog_example.model.dto.post.detail;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class PostDetailSaveDTO {
    @NotBlank
    private String title;
    @NotNull
    private String content;
}
