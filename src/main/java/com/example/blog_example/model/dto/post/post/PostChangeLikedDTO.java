package com.example.blog_example.model.dto.post.post;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.PositiveOrZero;

@Schema(description = "게시글 좋아요 변경 요청 DTO")
@AllArgsConstructor
@Getter
public class PostChangeLikedDTO {
    @ApiModelProperty(name = "postNo", value = "게시글 번호", example = "1", required = true)
    @PositiveOrZero
    private Long postNo;
    @ApiModelProperty(name = "userNo", value = "유저 번호", example = "1", required = true)
    @PositiveOrZero
    private Long userNo;
}
