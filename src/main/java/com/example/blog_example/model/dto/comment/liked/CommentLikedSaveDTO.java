package com.example.blog_example.model.dto.comment.liked;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.PositiveOrZero;

@Schema(description = "댓글 좋아요 등록 요청 DTO")
@Builder
@AllArgsConstructor
@Getter
public class CommentLikedSaveDTO {
    @ApiModelProperty(name = "commentNo", value = "댓글 번호", example = "example", required = true)
    @PositiveOrZero
    private Long commentNo;

    @ApiModelProperty(name = "userNo", value = "유저 번호", example = "example", required = true)
    @PositiveOrZero
    private Long userNo;
}
