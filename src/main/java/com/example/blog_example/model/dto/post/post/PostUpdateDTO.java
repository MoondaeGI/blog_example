package com.example.blog_example.model.dto.post.post;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.*;

@Schema(description = "게시글 수정 요청 DTO")
@Builder
@AllArgsConstructor
@Getter
public class PostUpdateDTO {
    @ApiModelProperty(name = "postNo", value = "게시글 번호", example = "1", required = true)
    @PositiveOrZero
    private Long postNo;

    @ApiModelProperty(name = "upperCategoryNo", value = "상위 카테고리 번호", example = "1", required = true)
    @PositiveOrZero
    private Long upperCategoryNo;

    @ApiModelProperty(name = "lowerCategoryNo", value = "하위 카테고리 번호", example = "1", required = true)
    @PositiveOrZero
    private Long lowerCategoryNo;

    @ApiModelProperty(name = "title", value = "제목", example = "example", required = true)
    @NotBlank @Size(max = 30)
    private String title;

    @ApiModelProperty(name = "content", value = "내용", example = "example", required = true)
    @NotBlank
    private String content;
}
