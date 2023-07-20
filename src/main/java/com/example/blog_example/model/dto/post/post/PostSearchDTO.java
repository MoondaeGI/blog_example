package com.example.blog_example.model.dto.post.post;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Schema(name = "PostSearchDTO", description = "게시글 검색 DTO")
@Builder
@AllArgsConstructor
@Getter
public class PostSearchDTO {
    @ApiModelProperty(name = "title", value = "제목", example = "example")
    private String title;

    @ApiModelProperty(name = "content", value = "내용", example = "example")
    private String content;
}
