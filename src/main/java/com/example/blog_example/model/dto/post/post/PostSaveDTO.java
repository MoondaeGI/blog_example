package com.example.blog_example.model.dto.post.post;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Schema(description = "게시글 등록 요청 DTO")
@Builder
@AllArgsConstructor
@Getter
public class PostSaveDTO {
    @ApiModelProperty(name = "upperCategoryNo", value = "상위 카테고리 번호", example = "1", required = true)
    @PositiveOrZero
    private Long upperCategoryNo;

    @ApiModelProperty(name = "lowerCategoryNo", value = "하위 카테고리 번호", example = "1")
    private Long lowerCategoryNo;

    @ApiModelProperty(name = "userNo", value = "유저 번호", example = "1", required = true)
    @PositiveOrZero
    private Long userNo;

    @ApiModelProperty(name = "title", value = "제목", example = "example", required = true)
    @NotBlank @Max(30)
    private String title;

    @ApiModelProperty(name = "content", value = "내용", example = "example", required = true)
    @NotBlank
    private String content;
}
