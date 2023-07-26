package com.example.blog_example.model.dto.post.post;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.PositiveOrZero;

@Schema(name = "PostSearchByCategoryDTO", description = "카테고리로 게시글 검색 DTO")
@Builder
@AllArgsConstructor
@Getter
public class PostSearchByCategoryDTO {
    @ApiModelProperty(name = "userNo", value = "유저 번호", example = "1", required = true)
    @PositiveOrZero
    private Long userNo;
    @ApiModelProperty(name = "upperCategoryNo", value = "상위 카테고리 번호", example = "1")
    private Long upperCategoryNo;

    @ApiModelProperty(name = "lowerCategoryNo", value = "하위 카테고리 번호", example = "1")
    private Long lowerCategoryNo;
}
